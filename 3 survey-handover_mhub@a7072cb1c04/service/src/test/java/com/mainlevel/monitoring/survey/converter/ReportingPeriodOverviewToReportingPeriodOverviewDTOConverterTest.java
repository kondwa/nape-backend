package com.mainlevel.monitoring.survey.converter;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mainlevel.monitoring.common.constant.ProfileConstant;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodOverviewDTO;
import com.mainlevel.monitoring.survey.database.queryresult.ReportingPeriodOverview;

@SuppressWarnings("javadoc")
@ActiveProfiles(ProfileConstant.WITHOUT_SECURITY)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
    ReportingPeriodOverviewToReportingPeriodOverviewDTOConverterTest.TestConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
public class ReportingPeriodOverviewToReportingPeriodOverviewDTOConverterTest {

    public static class TestConfiguration {

        @Bean
        public ReportingPeriodOverviewToReportingPeriodOverviewDTOConverter converter() {
            return new ReportingPeriodOverviewToReportingPeriodOverviewDTOConverter();
        }
    }

    @Autowired
    private ReportingPeriodOverviewToReportingPeriodOverviewDTOConverter converet;

    @Test
    public void testConvert() {

        final ReportingPeriodOverview src = ReportingPeriodOverview.builder().editedBy("An user").end(new Date()).unitName("a funding area")
            .lastEdit(new Date()).reportPeriodGid(UUID.randomUUID().getLeastSignificantBits()).start(new Date()).status("NEW").templateRefId("ABC")
            .templateTitle("Survey title").templateVersion(13L).build();

        final ReportingPeriodOverviewDTO result = converet.convert(src);

        assertEquals(src.getEditedBy(), result.getLastEditUser());
        assertEquals(src.getEnd(), result.getEnd());
        assertEquals(src.getUnitName(), result.getUnitName());
        assertEquals(src.getLastEdit(), result.getLastEditTime());
        assertEquals(src.getReportPeriodGid(), result.getReportPeriodGid());
        assertEquals(src.getStart(), result.getStart());
        assertEquals(src.getStatus(), result.getStatus());
        assertEquals(src.getTemplateRefId(), result.getTemplateId());
        assertEquals(src.getTemplateTitle(), result.getTemplateTitle());
        assertEquals(src.getTemplateVersion(), result.getTemplateVersion());
    }

}