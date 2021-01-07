package com.mainlevel.monitoring.authentication.service.impl;

import static com.mainlevel.monitoring.common.constant.AlgorithmConstant.SHA_256;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mainlevel.monitoring.authentication.api.exception.PasswordGenerationFailedException;
import com.mainlevel.monitoring.authentication.service.PasswordService;

/**
 * Default implementation of {@link PasswordService}.
 */
@Service
public class PasswordServiceImpl implements PasswordService {

    private final Random random = new SecureRandom();

    @Override
    public String generateSalt() {
        final byte[] salt = new byte[22];
        random.nextBytes(salt);
        return encodeBase64String(salt);
    }

    @Override
    public String hashPassword(final String salt, final String password) {

        Assert.hasLength(salt, "Salt is not allowed to be null or empty.");
        Assert.hasLength(password, "Password is not allowed to be null empty.");

        if (salt.length() < 32) {
            throw new IllegalArgumentException("Salt must have a minimum size of 32");
        }

        final StringBuffer hexString = new StringBuffer();

        try {
            final MessageDigest md = MessageDigest.getInstance(SHA_256);

            final String text = salt + password;
            final byte[] digest = md.digest(text.getBytes(UTF_8));

            for (final byte element : digest) {
                final String hex = Integer.toHexString(0xff & element);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

        } catch (final Exception e) {

            throw new PasswordGenerationFailedException("Hashing Password failed.", e);
        }

        return hexString.toString();
    }

    @Override
    public boolean verifyPassword(final String passwordToVerify, final String salt, final String hashedPassword) {

        Assert.hasLength(hashedPassword, "Password to verify is not allowed to be null or empty.");

        return hashedPassword.equals(hashPassword(salt, passwordToVerify));
    }

}
