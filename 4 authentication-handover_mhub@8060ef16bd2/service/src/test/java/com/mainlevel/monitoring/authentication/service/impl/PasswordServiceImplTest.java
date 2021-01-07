package com.mainlevel.monitoring.authentication.service.impl;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.mainlevel.monitoring.authentication.service.PasswordService;
import com.mainlevel.monitoring.authentication.service.impl.PasswordServiceImpl;

@SuppressWarnings("javadoc")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PasswordServiceImplTest.TestConfiguration.class}, loader = AnnotationConfigContextLoader.class)
public class PasswordServiceImplTest {

    private static final String HASHED_PASSWORD = "a401a022de651488ec85d7728ef03fe8b81c38971ec0d57a72053fbcc0ee72a8";
    private static final String SALT = "jgZHVq7eayRBrtWW3wBsVJvgUg6FvhtE";
    private static final String PASSWORD = "H005cGee";

    public static class TestConfiguration {

        @Bean
        private PasswordService passwordService() {
            return new PasswordServiceImpl();
        }

    }

    @Autowired
    PasswordService passwordService;

    @Test
    public void testPasswordHashing() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        assertEquals(HASHED_PASSWORD, passwordService.hashPassword(SALT, PASSWORD));

    }

    @Test
    public void testPasswordHashingNotEquals() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        assertNotEquals(HASHED_PASSWORD, passwordService.hashPassword(SALT, PASSWORD.substring(0, PASSWORD.length() - 2)));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPasswordHashingWithSaltNull() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        passwordService.hashPassword(null, PASSWORD);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPasswordHashingWithSaltEmpty() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        passwordService.hashPassword(SALT, EMPTY);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPasswordHashingWithPasswordNull() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        passwordService.hashPassword(SALT, null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPasswordHashingWithPasswordEmpty() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        passwordService.hashPassword(SALT, EMPTY);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPasswordHashingWithSaltTooShort() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        passwordService.hashPassword(SALT.substring(0, 30), PASSWORD);

    }

    @Test
    public void testSaltGenerator() {
        for (int i = 0; i < 250; i++) {
            final String generatedSalt = passwordService.generateSalt();
            Assert.assertEquals(32, generatedSalt.length());
            System.out.println(generatedSalt);
        }
    }

    @Test
    public void testPasswordVerification() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        assertTrue(passwordService.verifyPassword(PASSWORD, SALT, HASHED_PASSWORD));
        assertFalse(passwordService.verifyPassword("test", SALT, HASHED_PASSWORD));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPasswordVerificationWithEmptyPassword() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        passwordService.verifyPassword(EMPTY, SALT, HASHED_PASSWORD);
    }

}
