package com.example.backlogtp.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordEncoderTest {

    PasswordEncoder passwordEncoder = new PasswordEncoder();

    @Test
    public void encodePasswordCorrectly() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Assertions.assertEquals(passwordEncoder.encode("test").length(), 44);
    }

    @Test
    public void goodPasswordShouldPass() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String candidate = "toto";
        String hash = passwordEncoder.encode(candidate);
        boolean match = passwordEncoder.matches(candidate, hash);
        Assertions.assertTrue(match);
    }

    @Test
    public void badPasswordShouldNotPass() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String candidate = "toto";
        String hash = passwordEncoder.encode(candidate);
        boolean match = passwordEncoder.matches("not toto", hash);
        Assertions.assertFalse(match);
    }
}
