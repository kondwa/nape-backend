package com.mainlevel.monitoring.common.service;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Handles easy access to a Java KeyStore.
 */
public interface KeyStoreService {

    /**
     * Returns the {@Link PrivateKey} from the {@link KeyStore}.
     *
     * @return {@Link PrivateKey}
     */
    PrivateKey getPrivateKey();

    /**
     * Returns the {@Link PublicKey} from the {@link PublicKey}.
     *
     * @return {@Link PublicKey}
     */
    PublicKey getPublicKey();

    /**
     * Encrypts and Base64 encodes a String.
     *
     * @param unencryptedValue the plain value
     * @return Encrypted and Base64 encoded {@link String}.
     */
    String encrypt(String unencryptedValue);

    /**
     * Decrypt an base64 and encrypted String.
     *
     * @param base64EncodedValue the encoded value
     * @return The decrypted String.
     */
    String decrypt(String base64EncodedValue);
}