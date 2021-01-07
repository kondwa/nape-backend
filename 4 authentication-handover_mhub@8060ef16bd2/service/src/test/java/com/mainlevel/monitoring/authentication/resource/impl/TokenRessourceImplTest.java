package com.mainlevel.monitoring.authentication.resource.impl;

import static com.mainlevel.monitoring.authentication.resource.links.TokenResourceLinkProvider.createLinkToGetToken;
import static com.mainlevel.monitoring.authentication.resource.links.TokenResourceLinkProvider.createLinkToVerifyToken;
import static com.mainlevel.monitoring.common.constant.ProfileConstant.WITHOUT_SECURITY;
import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_EVALUATION_VIEWER;
import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_SURVEY_VIEWER;
import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import org.springframework.data.web.config.HateoasAwareSpringDataWebConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mainlevel.monitoring.authentication.configuration.ResourceTestConfiguration;
import com.mainlevel.monitoring.authentication.resource.TokenResourceImpl;
import com.mainlevel.monitoring.common.security.model.JWTAuthentication;
import com.mainlevel.monitoring.common.security.model.SimpleDeserializableGrantedAuthority;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.common.service.LinkDiscoveryService;

@SuppressWarnings("javadoc")
@ActiveProfiles(WITHOUT_SECURITY)
@WebAppConfiguration
@SpringBootTest(classes = TokenRessourceImplTest.TestConfiguration.class)
@TestPropertySource(properties = {"spring.cloud.config.failFast : false"})
@RunWith(SpringJUnit4ClassRunner.class)
@Import(HateoasAwareSpringDataWebConfiguration.class)
public class TokenRessourceImplTest {

    @Import(ResourceTestConfiguration.class)
    public static class TestConfiguration {

        @Bean
        public TokenResourceImpl tokenResource() {
            return new TokenResourceImpl();
        }

        @Bean
        public AuthenticationAccessService authenticationAccessService() {
            return mock(AuthenticationAccessService.class);
        }

        @Bean
        public LinkDiscoveryService linkDiscoveryService() {
            return mock(LinkDiscoveryService.class);
        }

    }

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Autowired
    private LinkDiscoveryService linkDiscoveryService;

    @Autowired
    private WebApplicationContext webapplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webapplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
        reset(authenticationAccessService, linkDiscoveryService);
    }

    @Test
    public void testDoLogin() throws Exception {

        String jwt = "JWT";

        SimpleDeserializableGrantedAuthority authorityAssistant =
            SimpleDeserializableGrantedAuthority.builder().authority(ROLE_SURVEY_VIEWER).build();
        SimpleDeserializableGrantedAuthority authorityProjectlead =
            SimpleDeserializableGrantedAuthority.builder().authority(ROLE_EVALUATION_VIEWER).build();
        SimpleDeserializableGrantedAuthority authorityUser = SimpleDeserializableGrantedAuthority.builder().authority(ROLE_USER).build();

        List<SimpleDeserializableGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authorityAssistant);
        authorities.add(authorityProjectlead);
        authorities.add(authorityUser);

        Authentication authentication =
            JWTAuthentication.builder().name("torben.bock@prodyna.com").details(jwt).authorities(authorities).authenticated(true).build();

        when(authenticationAccessService.getCurrentUserId()).thenReturn(1L);
        when(authenticationAccessService.getAuthentication()).thenReturn(authentication);
        when(authenticationAccessService.getCurrentUsername()).thenReturn("torben.bock@prodyna.com");

        URI uri = createLinkToGetToken().toUri();
        mockMvc.perform(get(uri).accept(APPLICATION_JSON_UTF8)).andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.token").exists())
            .andExpect(jsonPath("$.token", equalTo(jwt))).andExpect(jsonPath("$.user").exists()).andExpect(jsonPath("$.user", equalTo("1")));
    }

    @Test
    public void testTokenIsValidEndpoint() throws Exception {
        URI uri = createLinkToVerifyToken().toUri();
        mockMvc.perform(get(uri).accept(APPLICATION_JSON_UTF8)).andExpect(status().isOk()).andDo(print());
    }
}
