package com.mainlevel.monitoring.surveyadmin.resource;

import static com.mainlevel.monitoring.admin.resource.links.SurveyTemplateResourceLinkProvider.createLinkToCreateSurvey;
import static com.mainlevel.monitoring.admin.resource.links.SurveyTemplateResourceLinkProvider.createLinkToDeleteSurveyVersion;
import static com.mainlevel.monitoring.admin.resource.links.SurveyTemplateResourceLinkProvider.createLinkToGetByVersion;
import static com.mainlevel.monitoring.admin.resource.links.SurveyTemplateResourceLinkProvider.createLinkToGetSurvey;
import static com.mainlevel.monitoring.admin.resource.links.SurveyTemplateResourceLinkProvider.createLinkToGetSurveys;
import static com.mainlevel.monitoring.admin.resource.links.SurveyTemplateResourceLinkProvider.createLinkToUpdateSurvey;
import static com.mainlevel.monitoring.admin.resource.links.SurveyTemplateResourceLinkProvider.createLinkToUpdateSurveySettings;
import static com.mainlevel.monitoring.common.constant.ProfileConstant.WITHOUT_SECURITY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainlevel.monitoring.admin.api.dto.template.SurveySettingsDTO;
import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateDTO;
import com.mainlevel.monitoring.admin.api.dto.template.TemplateDTO;
import com.mainlevel.monitoring.admin.exception.DefaultExceptionHandler;
import com.mainlevel.monitoring.admin.repository.entity.SurveySettingsEntity;
import com.mainlevel.monitoring.admin.repository.entity.SurveyTemplateEntity;
import com.mainlevel.monitoring.admin.resource.TemplateResourceImpl;
import com.mainlevel.monitoring.admin.service.SurveyTemplateService;
import com.mainlevel.monitoring.common.header.ContentTypeJSONHeader;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.surveyadmin.configuration.ResourceTestConfiguration;

@SuppressWarnings("javadoc")
@ActiveProfiles(WITHOUT_SECURITY)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SurveyTemplateResourceImplTest.TestConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
public class SurveyTemplateResourceImplTest {

    @Import(ResourceTestConfiguration.class)
    public static class TestConfiguration {

        @Bean
        public TemplateResourceImpl adminSurveyResource() {
            return new TemplateResourceImpl();
        }

        @Bean
        public SurveyTemplateService surveyService() {
            return mock(SurveyTemplateService.class);
        }

        @Bean
        public DefaultExceptionHandler defaultExceptionHandler() {
            return new DefaultExceptionHandler();
        }

        @Bean
        public AuthenticationAccessService authenticationAccessService() {
            return mock(AuthenticationAccessService.class);
        }

        @Bean
        @Primary
        public ContentTypeJSONHeader contentTypeJSONHeader() {
            return new ContentTypeJSONHeader();
        }
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SurveyTemplateService surveyService;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateSurvey() throws Exception {
        when(surveyService.saveSurveyTemplate(any(SurveyTemplateEntity.class))).then(inv -> {
            final SurveyTemplateEntity survey = inv.getArgumentAt(0, SurveyTemplateEntity.class);
            survey.setId("theId");
            survey.setVersion(0L);
            return survey;
        });

        final SurveyTemplateDTO input = SurveyTemplateDTO.builder().surveyName("survey").surveyTemplate(new TemplateDTO()).build();
        mvc.perform(post(createLinkToCreateSurvey().toUri()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(input))).andExpect(status().isCreated());
    }

    @Test
    public void testGetSurveys() throws Exception {

        when(surveyService.getActiveSurveys()).thenReturn(Arrays.asList());
        mvc.perform(get(createLinkToGetSurveys().toUri()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "base64token")).andExpect(status().isOk());
    }

    @Test
    public void testGetSurvey() throws Exception {
        when(surveyService.loadSurvey(anyString())).thenReturn(SurveyTemplateEntity.builder().id("anId").version(7L).build(),
            (SurveyTemplateEntity) null);

        mvc.perform(get(createLinkToGetSurvey("anId").toUri()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "base64token")).andExpect(status().isOk());

        mvc.perform(get(createLinkToGetSurvey("anId").toUri())).andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateSurvey() throws Exception {
        when(surveyService.updateSurveyTemplate(any(SurveyTemplateEntity.class)))
            .thenReturn(SurveyTemplateEntity.builder().id("1").version(7L).build());
        final SurveyTemplateDTO build = SurveyTemplateDTO.builder().surveyName("Test Survey").surveyTemplate(new TemplateDTO()).build();

        mvc.perform(put(createLinkToUpdateSurvey("1").toUri()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(build))).andExpect(status().isOk());
    }

    @Test
    public void testUpdateSurveySettings() throws Exception {
        when(surveyService.updateSurveySettings(any(String.class), any(SurveySettingsEntity.class)))
            .thenReturn(SurveyTemplateEntity.builder().id("1").version(7L).build());
        final SurveySettingsDTO inSettings = SurveySettingsDTO.builder().surveyName("Test Survey").build();

        mvc.perform(put(createLinkToUpdateSurveySettings("1").toUri()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(inSettings))).andExpect(status().isOk()).andExpect(jsonPath("$.identifier").value("1"));
    }

    @Test
    public void testDeleteSurvey() throws Exception {
        URI uri = createLinkToGetSurvey("1").toUri();

        mvc.perform(delete(uri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }

    @Test
    public void testLoadSurveyVersion() throws Exception {

        when(surveyService.loadSurveyVersion(anyString(), anyLong())).thenReturn(SurveyTemplateEntity.builder().id("1").version(7L).build());

        mvc.perform(get(createLinkToGetByVersion("1", 1L).toUri()).header("Authorization", "1")).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.identifier").value("1"));
    }

    @Test
    public void testDeactivateSurvey() throws Exception {
        final Map<String, Long> deleted = new HashMap<>();
        doAnswer((final InvocationOnMock inv) -> {
            final String id = inv.getArgumentAt(0, String.class);
            final Long verison = inv.getArgumentAt(1, Long.class);
            deleted.put(id, verison);
            return null;
        }).when(surveyService).deactivateSurveyVersion(anyString(), anyLong());
        final String id = UUID.randomUUID().toString();
        final Long version = new Random().nextLong();
        mvc.perform(delete(createLinkToDeleteSurveyVersion(id, version).toUri())).andExpect(status().isNoContent());
        assertEquals(deleted.get(id), version);
    }
}
