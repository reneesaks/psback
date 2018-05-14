package com.professionalstrangers.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class PropertiesEncryptorTest {

    @Test
    public void whenEncryptString_thenReturnEncryptedString() {

        // When
        PropertiesEncryptor propertiesEncryptor = new PropertiesEncryptor();
        String encrypted = ReflectionTestUtils
                .invokeMethod(
                        propertiesEncryptor, "encryptString", "secret", "password", "salt"
                );
        String decrypted = ReflectionTestUtils
                .invokeMethod(
                        propertiesEncryptor, "decryptString", encrypted, "password", "salt"
                );

        // Then
        Assert.assertEquals("secret", decrypted);
    }
}
