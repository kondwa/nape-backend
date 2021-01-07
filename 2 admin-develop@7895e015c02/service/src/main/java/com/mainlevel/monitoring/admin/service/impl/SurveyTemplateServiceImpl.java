package com.mainlevel.monitoring.admin.service.impl;

import static com.mainlevel.monitoring.admin.service.model.QuestionTypes.DYNAMIC_TABLE;
import static com.mainlevel.monitoring.admin.service.model.QuestionTypes.PREDEFINED_LIST;
import static com.mainlevel.monitoring.admin.service.model.QuestionTypes.STATIC_TABLE;
import static org.springframework.util.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.admin.api.exception.SurveyNotFoundException;
import com.mainlevel.monitoring.admin.repository.SurveyTemplateRepository;
import com.mainlevel.monitoring.admin.repository.TemplateRepository;
import com.mainlevel.monitoring.admin.repository.entity.ChoiceEntity;
import com.mainlevel.monitoring.admin.repository.entity.PageEntity;
import com.mainlevel.monitoring.admin.repository.entity.PredefinedListItemEntity;
import com.mainlevel.monitoring.admin.repository.entity.QuestionEntity;
import com.mainlevel.monitoring.admin.repository.entity.SurveySettingsEntity;
import com.mainlevel.monitoring.admin.repository.entity.SurveyTemplateEntity;
import com.mainlevel.monitoring.admin.repository.entity.TemplateEntity;
import com.mainlevel.monitoring.admin.repository.entity.TemplateStatus;
import com.mainlevel.monitoring.admin.repository.entity.UnitEntity;
import com.mainlevel.monitoring.admin.repository.entity.UserEntity;
import com.mainlevel.monitoring.admin.repository.entity.ValueItemEntity;
import com.mainlevel.monitoring.admin.service.LanguageService;
import com.mainlevel.monitoring.admin.service.PredefinedListService;
import com.mainlevel.monitoring.admin.service.SurveyTemplateService;
import com.mainlevel.monitoring.admin.service.UnitService;
import com.mainlevel.monitoring.admin.service.UserService;
import com.mainlevel.monitoring.admin.service.util.SurveyTemplatePermissionVisitor;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link SurveyTemplateService}.
 */
@Service
@Slf4j
public class SurveyTemplateServiceImpl implements SurveyTemplateService {

    private static final String DEFAULT_LANGUAGE = "de";

    @Autowired
    private SurveyTemplateRepository surveyTemplateRepository;

    @Autowired
    private TemplateRepository templateRepo;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private UnitService organizationalUnitService;

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Autowired
    private PredefinedListService predefinedListService;

    @Autowired
    private UserService userService;

    @Override
    public SurveyTemplateEntity saveSurveyTemplate(SurveyTemplateEntity survey) {

        survey.setActive(Boolean.TRUE);
        survey.setStatus(TemplateStatus.DRAFT.getName());

        long userId = authenticationAccessService.getCurrentUserId();
        survey.setUpdatedById(userId);

        loadReferences(survey);

        SurveyTemplateEntity savedSurvey = surveyTemplateRepository.save(survey);
        TemplateEntity template = prepareTemplate(savedSurvey);
        if (template != null) {
            template.setUpdatedById(userId);

            TemplateEntity dbTemplate = templateRepo.insert(template);
            savedSurvey.setTemplate(dbTemplate);
        }

        return savedSurvey;
    }

    @Override
    public SurveyTemplateEntity updateSurveyTemplate(SurveyTemplateEntity survey) {

        SurveyTemplateEntity dbSurvey = prepareSurvey(survey);
        if (dbSurvey == null) {
            throw new SurveyNotFoundException(survey.getId());
        }

        long userId = authenticationAccessService.getCurrentUserId();
        dbSurvey.setUpdatedById(userId);

        dbSurvey = surveyTemplateRepository.save(dbSurvey);

        if (dbSurvey.getTemplate() != null) {
            TemplateEntity template = prepareTemplate(dbSurvey);
            if (template != null) {
                template.setUpdatedById(userId);

                TemplateEntity dbTemplate = templateRepo.insert(template);
                dbSurvey.setTemplate(dbTemplate);
            }
        }

        return dbSurvey;
    }

    @Override
    public SurveyTemplateEntity updateSurveySettings(String surveyId, SurveySettingsEntity settings) {

        SurveyTemplateEntity survey = loadSurvey(surveyId);
        if (survey == null) {
            throw new SurveyNotFoundException(surveyId);
        }

        survey.setSurveyName(settings.getSurveyName());
        survey.setUnit(settings.getUnit());
        survey.setLanguage(settings.getLanguage());
        survey = updateSurveyTemplate(survey);

        return survey;
    }

    @Override
    public void deactivateSurvey(String id) {
        SurveyTemplateEntity survey = surveyTemplateRepository.findOne(id);
        if (survey == null) {
            throw new SurveyNotFoundException(id);
        }

        survey.setActive(Boolean.FALSE);
        surveyTemplateRepository.save(survey);
    }

    @Override
    public List<SurveyTemplateEntity> getActiveSurveys() {
        List<SurveyTemplateEntity> surveyList = surveyTemplateRepository.findByActiveIsTrue();
        return surveyList;
    }

    @Override
    public SurveyTemplateEntity loadSurvey(String id) {
        SurveyTemplateEntity survey = surveyTemplateRepository.findOne(id);

        List<TemplateEntity> list =
            templateRepo.findBySurvey(true, survey.getId(), new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "version"))).getContent();

        if (!list.isEmpty()) {
            survey.setTemplate(list.get(0));
        }

        loadPredefinedLists(survey);
        return survey;
    }

    private SurveyTemplateEntity prepareSurvey(SurveyTemplateEntity survey) {
        SurveyTemplateEntity dbSurvey = surveyTemplateRepository.findOne(survey.getId());
        if (dbSurvey != null) {
            dbSurvey.setSurveyName(survey.getSurveyName());
            dbSurvey.setTemplate(survey.getTemplate());
            dbSurvey.setLanguage(survey.getLanguage());
            dbSurvey.setUnit(survey.getUnit());
            dbSurvey.setDescription(survey.getDescription());
            loadReferences(dbSurvey);
            dbSurvey.setStatus(TemplateStatus.DRAFT.getName());
        }
        return dbSurvey;
    }

    private TemplateEntity prepareTemplate(SurveyTemplateEntity survey) {
        TemplateEntity template = survey.getTemplate();
        if (template != null) {
            template.setSurvey(survey);
            template.setUpdated(survey.getLastModifiedDate());
            template.setVersion(survey.getVersion());
            template.setUpdatedById(survey.getUpdatedById());
            template.setActive(Boolean.TRUE);
            template.setStatus(survey.getStatus());
            template.setTitle(survey.getSurveyName());
            template.setDescription(survey.getDescription());
        }

        return template;
    }

    @Override
    public Set<SurveyTemplateEntity> loadSurveysByFilter(String unitId) {

        String username = authenticationAccessService.getCurrentUsername();
        UserEntity user = this.userService.findByUserName(username);

        List<SurveyTemplateEntity> templates;
        if (unitId != null) {
            templates = surveyTemplateRepository.findByActiveAndUnitId(true, unitId);
        } else {
            templates = surveyTemplateRepository.findByActiveIsTrue();
        }

        log.info("Found {} surveys before permission check. Filtered by unitId: {}", templates.size(), unitId);
        
        SurveyTemplatePermissionVisitor visitor = new SurveyTemplatePermissionVisitor(templates);
        user.getParticipations().stream().filter(p -> p.getUnit() != null).forEach(p -> p.getUnit().accept(visitor));

        Set<SurveyTemplateEntity> result = visitor.getPermittedTemplates();

        return result;
    }

    private void loadReferences(SurveyTemplateEntity survey) {

        if (survey != null) {
            if (survey.getLanguage() != null && survey.getLanguage().getId() != null) {
                survey.setLanguage(languageService.load(survey.getLanguage().getId()));
            }
            if (survey.getUnit() != null && survey.getUnit().getId() != null) {
                UnitEntity unit = organizationalUnitService.findUnitById(survey.getUnit().getId());
                survey.setUnit(unit);
            }
        }
    }

    @Override
    public List<TemplateEntity> getHistory(String surveyId) {

        List<TemplateEntity> templates =
            templateRepo.findBySurvey(true, surveyId, new PageRequest(0, Integer.MAX_VALUE, new Sort(Sort.Direction.DESC, "version"))).getContent();

        if (!templates.isEmpty()) {
            templates = templates.subList(1, templates.size());
        }

        return templates;
    }

    @Override
    public SurveyTemplateEntity loadSurveyVersion(String id, Long version) {
        SurveyTemplateEntity survey = surveyTemplateRepository.findOne(id);

        List<TemplateEntity> list = templateRepo.findBySurveyAndVersion(true, survey.getId(), version, new PageRequest(0, 1)).getContent();

        if (!list.isEmpty()) {
            TemplateEntity template = list.get(0);
            survey.setTemplate(template);

            if (!isEmpty(template.getTitle())) {
                survey.setSurveyName(template.getTitle());
            }

            survey.setDescription(template.getDescription());
        } else {
            survey.setTemplate(null);
        }

        loadPredefinedLists(survey);

        return survey;
    }

    @Override
    public void deactivateSurveyVersion(String id, Long version) {

        List<TemplateEntity> list = templateRepo.findBySurveyAndVersion(true, id, version, new PageRequest(0, 1)).getContent();

        if (!list.isEmpty()) {
            TemplateEntity template = list.get(0);
            template.setActive(Boolean.FALSE);
            templateRepo.save(template);
        } else {
            throw new SurveyNotFoundException(id);
        }

    }

    @Override
    public void doWorkflowActive(SurveyTemplateEntity survey) {
        surveyTemplateRepository.updateStatus(survey.getId(), TemplateStatus.ACTIVE.getName());

        if (survey.getTemplate() != null) {
            survey.getTemplate().setStatus(TemplateStatus.ACTIVE.getName());
            templateRepo.save(survey.getTemplate());
        }
    }

    /**
     * Load the predefined lists and add choices and values to the questions.
     *
     * @param survey the survey template
     */
    private void loadPredefinedLists(SurveyTemplateEntity survey) {

        if (survey != null && survey.getTemplate() != null && survey.getTemplate().getPages() != null) {

            // if we don't have predefined language default is german.
            String language = survey.getLanguage() != null ? survey.getLanguage().getId() : DEFAULT_LANGUAGE;

            for (PageEntity page : survey.getTemplate().getPages()) {

                if (page.getQuestions() != null) {
                    loadPredefinedListsForPage(language, page);
                }
            }
        }
    }

    /**
     * @param language
     * @param page
     */
    private void loadPredefinedListsForPage(String language, PageEntity page) {

        // go through every question and check its type.
        for (QuestionEntity question : page.getQuestions()) {

            if (PREDEFINED_LIST.equalsIgnoreCase(question.getType())) {

                String listType = question.getPredefinedListType();
                question.setChoices(new ArrayList<>());

                List<PredefinedListItemEntity> items = predefinedListService.getItems(listType);
                items.forEach(item -> {
                    ValueItemEntity value = ValueItemEntity.builder().name(item.getCode()).value(item.getId()).build();
                    question.getChoices().add(value);
                });

            } else if (STATIC_TABLE.equalsIgnoreCase(question.getType()) || DYNAMIC_TABLE.equalsIgnoreCase(question.getType())) {

                for (ValueItemEntity column : question.getColumns()) {

                    if (PREDEFINED_LIST.equalsIgnoreCase(column.getType())) {

                        String listType = column.getPredefinedListType();
                        column.setChoices(new ArrayList<>());

                        List<PredefinedListItemEntity> items = predefinedListService.getItems(listType);

                        items.forEach(item -> {
                            ChoiceEntity choice = ChoiceEntity.builder().name(item.getCode()).value(item.getId()).build();
                            column.getChoices().add(choice);
                        });
                    }
                }
            }
        }
    }

}
