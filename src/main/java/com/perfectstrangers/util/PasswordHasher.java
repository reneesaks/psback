package com.perfectstrangers.util;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

public class PasswordHasher {

    public String hashPasswordWithSha256(String password) {
        return sha256Hex(password);
    }

}
