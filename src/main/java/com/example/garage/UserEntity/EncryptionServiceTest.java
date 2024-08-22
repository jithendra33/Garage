package com.example.garage.UserEntity;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.garage.UserService.EncryptionService;

public class EncryptionServiceTest {

    @Autowired
    private EncryptionService encryptionService;

    @Test
    public void testEncryptAndVerifyPassword() {
        String password = "mySecretPassword";
        String encryptedPassword = encryptionService.encryptPassword(password);
        
        // Verify the password
        assertTrue(encryptionService.verifyPassword(password, encryptedPassword));
    }
}
