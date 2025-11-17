package com.example.backlogtp.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordEncoder {

    private final String salt = "verySecureSecret";

    public String encode(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec keySpec = new PBEKeySpec(
                password.toCharArray(),
                Base64.getDecoder().decode(salt),
                65536,
                256
        );

        SecretKeyFactory skt = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = skt.generateSecret(keySpec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    public boolean matches(String candidate, String hash) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return hash.equals(encode(candidate));
    }
}
