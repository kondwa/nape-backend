package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodOverviewDTO;
import com.mainlevel.monitoring.survey.database.queryresult.ReportingPeriodOverview;

/**
 * Converter for converting ReportingPeriodOverview to SurveyOverviewDTO.
 */
@Component
public class ReportingPeriodOverviewToReportingPeriodOverviewDTOConverter
    extends AbstractApplicationAwareConverter<ReportingPeriodOverview, ReportingPeriodOverviewDTO> {

    @Override
    public ReportingPeriodOverviewDTO convert(ReportingPeriodOverview source) {

        ReportingPeriodOverviewDTO target = ReportingPeriodOverviewDTO.builder().lastEditUser(source.getEditedBy()).end(source.getEnd())
            .reportPeriodGid(source.getReportPeriodGid()).start(source.getStart()).status(source.getStatus()).templateId(source.getTemplateRefId())
            .templateTitle(source.getTemplateTitle()).templateVersion(source.getTemplateVersion()).unitName(source.getUnitName())
            .author(source.getAuthor()).owner(source.getOwner()).originalOwner(source.getOriginalOwner()).creationTime(source.getCreationTime())
            .lastEditTime(source.getLastEdit()).lastEditAddress(source.getEditedByAddress()).lastEditClientType(source.getEditedByClient())
            .questionsTotal(source.getNumberOfQuestions()).questionsAnswered(source.getNumberOfAnsweredQuestions()).build();

        return target;
    }

}
