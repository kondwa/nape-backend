package com.mainlevel.monitoring.common.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.common.service.impl.CollectionConversionServiceImpl;
import com.mainlevel.monitoring.common.service.impl.CollectionConversionServiceImplTest.TestConfiguration;

import lombok.Builder;
import lombok.Data;

@SuppressWarnings("javadoc")
@SpringBootTest(classes = TestConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CollectionConversionServiceImplTest {

    private static final String USERNAME_1 = "Test1";
    private static final String USERNAME_2 = "Test2";

    public static class TestConfiguration {

        @Bean
        public CollectionConversionService collectionConversionService() {
            return new CollectionConversionServiceImpl();
        }

        @Bean
        public ConversionService conversionService() {
            return mock(ConversionService.class);
        }
    }

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Autowired
    private ConversionService conversionService;

    private final UserDTO userDTO1 = UserDTO.builder().username(USERNAME_1).build();
    private final UserDTO userDTO2 = UserDTO.builder().username(USERNAME_2).build();

    private final User user1 = User.builder().username(USERNAME_1).build();
    private final User user2 = User.builder().username(USERNAME_2).build();
    private final ArrayList<User> users = new ArrayList<>(2);

    public CollectionConversionServiceImplTest() {
        users.add(user1);
        users.add(user2);
    }

    @Before
    public void setUp() throws Exception {
        when(conversionService.convert(eq(user1), eq(UserDTO.class))).thenReturn(userDTO1);

        when(conversionService.convert(eq(user2), eq(UserDTO.class))).thenReturn(userDTO2);
    }

    @After
    public void tearDown() throws Exception {
        reset(conversionService);
    }

    @Test
    public void testConvertWithEmptyCollection() {
        final List<UserDTO> userDTOs = collectionConversionService.convert(Collections.emptyList(), UserDTO.class);

        assertEquals(0, userDTOs.size());
    }

    @Test
    public void testConvertWithNullCollection() {
        final List<UserDTO> userDTOs = collectionConversionService.convert(null, UserDTO.class);

        assertEquals(0, userDTOs.size());
    }

    @Test
    public void testConvert() {
        final List<UserDTO> userDTOs = collectionConversionService.convert(users, UserDTO.class);

        assertEquals(2, userDTOs.size());
        assertTrue(userDTOs.parallelStream().anyMatch(userDTO -> userDTO.getUsername().equals(USERNAME_1)));
        assertTrue(userDTOs.parallelStream().anyMatch(userDTO -> userDTO.getUsername().equals(USERNAME_2)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testConvertWithConsumer() {
        final Consumer<UserDTO> simpleConsumer = mock(Consumer.class);

        final List<UserDTO> userDTOs = collectionConversionService.convert(users, UserDTO.class, simpleConsumer);

        assertEquals(2, userDTOs.size());
        assertTrue(userDTOs.parallelStream().anyMatch(userDTO -> userDTO.getUsername().equals(USERNAME_1)));
        assertTrue(userDTOs.parallelStream().anyMatch(userDTO -> userDTO.getUsername().equals(USERNAME_2)));

        verify(simpleConsumer).accept(userDTO1);
        verify(simpleConsumer).accept(userDTO2);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testConvertWithConsumerWithEmptyCollection() {
        final Consumer<UserDTO> simpleConsumer = mock(Consumer.class);

        final List<UserDTO> userDTOs = collectionConversionService.convert(Collections.emptyList(), UserDTO.class, simpleConsumer);

        assertEquals(0, userDTOs.size());
    }

    @Test
    public void testConvertWithConsumerWithNullConsumer() {
        final List<UserDTO> userDTOs = collectionConversionService.convert(users, UserDTO.class, null);

        assertEquals(2, userDTOs.size());
        assertTrue(userDTOs.parallelStream().anyMatch(userDTO -> userDTO.getUsername().equals(USERNAME_1)));
        assertTrue(userDTOs.parallelStream().anyMatch(userDTO -> userDTO.getUsername().equals(USERNAME_2)));
    }

    @Builder
    @Data
    static class UserDTO {

        private String username;
    }

    @Builder
    @Data
    static class User {

        private String username;
    }
}
