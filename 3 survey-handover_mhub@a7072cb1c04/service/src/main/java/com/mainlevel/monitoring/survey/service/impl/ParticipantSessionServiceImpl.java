/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.survey.database.node.Answer;
import com.mainlevel.monitoring.survey.database.node.Client;
import com.mainlevel.monitoring.survey.database.node.ParticipantSession;
import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;
import com.mainlevel.monitoring.survey.database.node.User;
import com.mainlevel.monitoring.survey.database.node.question.Question;
import com.mainlevel.monitoring.survey.database.repository.AnswerRepository;
import com.mainlevel.monitoring.survey.database.repository.ClientRepository;
import com.mainlevel.monitoring.survey.database.repository.ParticipantSessionRepository;
import com.mainlevel.monitoring.survey.database.repository.UserRepository;
import com.mainlevel.monitoring.survey.service.ParticipantSessionService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link ParticipantSessionService}.
 */
@Slf4j
@Service
public class ParticipantSessionServiceImpl implements ParticipantSessionService {

    @Autowired
    private ParticipantSessionRepository participantSessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ParticipantSession getLatestParticipantSession(Long reportingPeriodGid) {
        return getLatestsSession(reportingPeriodGid);
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public ParticipantSession createParticipantSession(ReportingPeriod period, List<Answer> answers) {

        ParticipantSession prevSession = getLatestParticipantSession(period.getGid());
        if (prevSession != null) {
            prevSession.setPeriod(null);
        }

        Date currentTimestamp = new Date();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedTime = LocalDateTime.now().format(formatter);

        String username = authenticationAccessService.getCurrentUsername();
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            log.error("User with username {} does not exist in neo4j.", username);
            throw new RuntimeException("User with username " + username + " does not exist in neo4j.");
        }

        final ParticipantSession newSession = ParticipantSession.builder().time(currentTimestamp).formattedTime(formattedTime).period(period)
            .prevVersion(prevSession).user(user).answers(answers).build();

        log.info("Created a new ParticipantSession for user {}, time: {}", user.getUsername(), newSession.getTime());

        return newSession;
    }

    /**
     * Load the latest participant session flat from the repository.
     *
     * @param reportingPeriodGid the reporting period id
     * @return the session without answers
     */
    private ParticipantSession getLatestsSession(Long reportingPeriodGid) {
        ParticipantSession prevSession = null;
        List<ParticipantSession> prevSessions = participantSessionRepository.findByPeriodId(reportingPeriodGid);

        if (prevSessions != null && !prevSessions.isEmpty()) {
            if (prevSessions.size() > 1) {
                log.warn("Found {} previous participant sessions for reporting period: {}", prevSessions.size(), reportingPeriodGid);
            }
            prevSession = prevSessions.get(0);
        }

        return prevSession;
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = false)
    public ParticipantSession saveParticipantSession(final ParticipantSession ps) {

        if (ps.getAnswers() != null) {
            Iterable<Answer> answers = answerRepository.save(ps.getAnswers(), 0);

            // TODO: Performance: one query
            for (Answer answer : answers) {
                if (answer.getQuestion() != null && answer.getQuestion().getQuestion() != null) {
                    String title = answer.getQuestion().getTitle();
                    Question question = answer.getQuestion().getQuestion();
                    Integer order = answer.getQuestion().getOrder();
                    answerRepository.saveQuestionRelation(answer.getGid(), question.getGid(), title, order);
                }
                if (answer.getOption() != null) {
                    answerRepository.saveOptionRelation(answer.getGid(), answer.getOption().getGid());
                }
            }
        }

        if (ps.getClient() != null) {
            // TODO Index!
            Client client = clientRepository.findByAttributes(ps.getClient().getAddress(), ps.getClient().getBrowser(), ps.getClient().getOs(),
                ps.getClient().getType());

            if (client != null) {
                ps.setClient(client);
            }
        }

        ParticipantSession session = participantSessionRepository.save(ps, 1);

        if (ps.getPrevVersion() != null) {
            participantSessionRepository.deleteRelationToReportingPeriod(ps.getPrevVersion().getGid(), ps.getPeriod().getGid());
        }

        return session;
    }

}
