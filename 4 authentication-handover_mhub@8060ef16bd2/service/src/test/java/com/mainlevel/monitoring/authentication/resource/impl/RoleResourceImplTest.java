package com.mainlevel.monitoring.authentication.resource.impl;

import static com.mainlevel.monitoring.common.constant.ProfileConstant.WITHOUT_SECURITY;
import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_SURVEY_VIEWER;
import static com.mainlevel.monitoring.common.constant.RoleConstant.USER;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.web.config.HateoasAwareSpringDataWebConfiguration;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainlevel.monitoring.authentication.api.dto.RoleDTO;
import com.mainlevel.monitoring.authentication.api.dto.RoleListDTO;
import com.mainlevel.monitoring.authentication.configuration.ResourceTestConfiguration;
import com.mainlevel.monitoring.authentication.exception.DefaultExceptionHandler;
import com.mainlevel.monitoring.authentication.resource.RoleResourceImpl;
import com.mainlevel.monitoring.authentication.resource.links.RoleResourceLinkProvider;
import com.mainlevel.monitoring.authentication.service.RoleService;
import com.mainlevel.monitoring.common.header.ContentTypeJSONHeader;

@SuppressWarnings("javadoc")
@ActiveProfiles(WITHOUT_SECURITY)
@WebAppConfiguration
@SpringBootTest(classes = RoleResourceImplTest.TestConfiguration.class)
@TestPropertySource(properties = {"spring.cloud.config.failFast : false"})
@RunWith(SpringJUnit4ClassRunner.class)
@Import(HateoasAwareSpringDataWebConfiguration.class)
public class RoleResourceImplTest {

    @Import(ResourceTestConfiguration.class)
    public static class TestConfiguration {

        @Bean
        public RoleResourceImpl roleResource() {
            return new RoleResourceImpl();
        }

        @Bean
        public RoleService roleService() {
            return mock(RoleService.class);
        }

        @Bean
        @Primary
        public ContentTypeJSONHeader contentTypeJSONHeader() {
            return new ContentTypeJSONHeader();
        }

        @Bean
        public DefaultExceptionHandler exceptionHandler() {
            return new DefaultExceptionHandler();
        }

    }

    @Autowired
    private WebApplicationContext webapplicationContext;

    @Autowired
    private RoleService roleService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webapplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
        reset(roleService);
    }

    @Test
    public void testGetRoles() throws Exception {

        final List<RoleDTO> roles = new ArrayList<>();
        roles.add(createRole(1L, ROLE_SURVEY_VIEWER));
        roles.add(createRole(2L, USER));

        when(roleService.getRoles()).thenReturn(roles);

        final URI uri = RoleResourceLinkProvider.createLinkToGetRoles().toUri();

        final MvcResult mvcResult = mockMvc.perform(get(uri).accept(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        final MockHttpServletResponse response = mvcResult.getResponse();

        final String content = response.getContentAsString();
        final RoleListDTO rolesDTO = new ObjectMapper().readValue(content, RoleListDTO.class);

        assertEquals(2, rolesDTO.getRoles().size());
    }

    private RoleDTO createRole(final long id, final String roleConstant) {
        return RoleDTO.builder().identifier(id).name(roleConstant).build();
    }
}
