package com.mainlevel.monitoring.common.service.impl;

import static com.mainlevel.monitoring.common.constant.AlgorithmConstant.AES;
import static com.mainlevel.monitoring.common.constant.AlgorithmConstant.SHA_256;
import static com.mainlevel.monitoring.common.constant.EncodingConstant.UTF_8;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static org.bouncycastle.util.Arrays.copyOf;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.common.service.KeyStoreService;

/**
 * Default implementation of {@link KeyStoreService}.
 */
@Service
public class KeyStoreServiceImpl implements KeyStoreService {

    private final String AES_ECB_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private final String CLASSPATH_PREFIX = "classpath:";

    @Value("${keyStore.path}")
    private String keyStorePath;

    @Value("${keyStore.alias}")
    private String keyStoreAlias;

    @Value("${keyStore.password}")
    private char[] keyStorePassword;

    @Value("${jwt.password}")
    private char[] jwtPassword;

    private KeyStore keyStore;

    /**
     * Called after bean creation. Load the keystore instance in memory.
     *
     * @throws Exception when the keystore cannot be loaded
     */
    @PostConstruct
    void postConstruct() throws Exception {
        final InputStream jksInputStream = keyStorePath.startsWith(CLASSPATH_PREFIX)
            ? getClass().getClassLoader().getResourceAsStream(keyStorePath.substring(CLASSPATH_PREFIX.length())) : new FileInputStream(keyStorePath);

        keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(jksInputStream, keyStorePassword);
    }

    @Override
    public PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate(keyStoreAlias).getPublicKey();
        } catch (final Exception exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }

    @Override
    public PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey(keyStoreAlias, keyStorePassword);
        } catch (final Exception exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }

    private SecretKey getSecretKey() {
        try {
            final byte[] keyInBytes = new String(jwtPassword).getBytes(UTF_8);
            final MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
            return new SecretKeySpec(messageDigest.digest(keyInBytes), AES);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }

    private IvParameterSpec getIvParameterSpec() throws UnsupportedEncodingException {
        return new IvParameterSpec(copyOf(new String(jwtPassword).getBytes(UTF_8), 16));
    }

    @Override
    public String encrypt(final String unencryptedValue) {
        try {
            final Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5_PADDING);
            cipher.init(ENCRYPT_MODE, getSecretKey(), getIvParameterSpec());
            final byte[] encryptedBytes = cipher.doFinal(unencryptedValue.getBytes(UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (final Exception exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }

    @Override
    public String decrypt(final String base64EncodedValue) {
        try {
            final Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5_PADDING);
            cipher.init(DECRYPT_MODE, getSecretKey(), getIvParameterSpec());
            final byte[] unencodedEncryptedBytes = Base64.getDecoder().decode(base64EncodedValue);
            final byte[] unencryptedBytes = cipher.doFinal(unencodedEncryptedBytes);
            return new String(unencryptedBytes, UTF_8);
        } catch (final Exception exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }
}
