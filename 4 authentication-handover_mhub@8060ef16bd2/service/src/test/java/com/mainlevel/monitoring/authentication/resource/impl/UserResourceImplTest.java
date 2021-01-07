package com.mainlevel.monitoring.authentication.resource.impl;

import static com.mainlevel.monitoring.common.constant.ProfileConstant.WITHOUT_SECURITY;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.web.config.HateoasAwareSpringDataWebConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mainlevel.monitoring.authentication.api.dto.RoleDTO;
import com.mainlevel.monitoring.authentication.api.dto.UserDTO;
import com.mainlevel.monitoring.authentication.configuration.ResourceTestConfiguration;
import com.mainlevel.monitoring.authentication.exception.DefaultExceptionHandler;
import com.mainlevel.monitoring.authentication.resource.UserResourceImpl;
import com.mainlevel.monitoring.authentication.resource.links.UserResourceLinkProvider;
import com.mainlevel.monitoring.authentication.service.UserService;
import com.mainlevel.monitoring.common.header.ContentTypeJSONHeader;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;

@SuppressWarnings("javadoc")
@ActiveProfiles(WITHOUT_SECURITY)
@WebAppConfiguration
@SpringBootTest(classes = UserResourceImplTest.TestConfiguration.class)
@TestPropertySource(properties = {"spring.cloud.config.failFast : false"})
@RunWith(SpringJUnit4ClassRunner.class)
@Import(HateoasAwareSpringDataWebConfiguration.class)
public class UserResourceImplTest {

    private static final String ROLE_NAME = "name";

    private static final boolean ACTIVE = true;

    private static final String PASSWORD = "password";

    private static final String USERNAME = "username";

    private static final String LASTNAME = "lastname";

    private static final String FIRSTNAME = "firstname";

    private static final RoleDTO ROLE_DTO = RoleDTO.builder().identifier(1l).name(ROLE_NAME).build();

    @Import(ResourceTestConfiguration.class)
    public static class TestConfiguration {

        @Bean
        public UserResourceImpl userResouce() {
            return new UserResourceImpl();
        }

        @Bean
        public UserService userService() {
            return mock(UserService.class);
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

        @Bean
        public DefaultExceptionHandler exceptionHandler() {
            return new DefaultExceptionHandler();
        }

    }

    @Autowired
    private WebApplicationContext webapplicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webapplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetCurrentUser() throws Exception {

        final UserDTO userDTO = UserDTO.builder().identifier(1L).firstName(FIRSTNAME).lastName(LASTNAME).username(USERNAME).password(PASSWORD)
            .active(ACTIVE).roles(singletonList(ROLE_DTO)).build();

        when(authenticationAccessService.getCurrentUserId()).thenReturn(1L);
        when(userService.getUser(Matchers.anyLong())).thenReturn(userDTO);

        final URI uri = UserResourceLinkProvider.createLinkToGetCurrentUser().toUri();

        mockMvc.perform(get(uri).accept(APPLICATION_JSON)).andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.username").value(USERNAME))
            .andExpect(jsonPath("$.firstName").value(FIRSTNAME)).andExpect(jsonPath("$.lastName").value(LASTNAME))
            .andExpect(jsonPath("$.password").value(PASSWORD)).andExpect(jsonPath("$.active").value(ACTIVE))
            .andExpect(jsonPath("$.roles[0].name").value(ROLE_NAME));

    }

    @Test
    public void testGetSpecificUser() throws Exception {

        final UserDTO userDTO = UserDTO.builder().identifier(1L).firstName(FIRSTNAME).lastName(LASTNAME).username(USERNAME).password(PASSWORD)
            .active(ACTIVE).roles(singletonList(ROLE_DTO)).build();

        when(userService.getUser(Matchers.anyLong())).thenReturn(userDTO);

        final URI uri = UserResourceLinkProvider.createLinkToGetUser(655L).toUri();

        mockMvc.perform(get(uri).accept(APPLICATION_JSON)).andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.username").value(USERNAME))
            .andExpect(jsonPath("$.firstName").value(FIRSTNAME)).andExpect(jsonPath("$.lastName").value(LASTNAME))
            .andExpect(jsonPath("$.password").value(PASSWORD)).andExpect(jsonPath("$.active").value(ACTIVE))
            .andExpect(jsonPath("$.roles[0].name").value(ROLE_NAME));

    }

}
