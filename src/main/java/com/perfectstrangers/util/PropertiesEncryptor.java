package com.perfectstrangers.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encrypts and decrypts properties files
 */
public class PropertiesEncryptor {

    /**
     * Encrypt a string with given password
     *
     * @param str string to encrypt
     * @param password password to use
     * @return encrypted string
     */
    private static String encryptString(String str, String password) {
        byte[] KeyData = password.getBytes();
        SecretKeySpec blowfish = new SecretKeySpec(KeyData, "Blowfish");
        Cipher cipher = null;
        String encryptedString = null;

        try {
            cipher = Cipher.getInstance("Blowfish");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            System.out.println("Something went wrong with the encryption");
        }

        try {
            assert cipher != null;
            cipher.init(Cipher.ENCRYPT_MODE, blowfish);
        } catch (InvalidKeyException e) {
            System.out.println("Invalid encryption key");
        }

        try {
            Base64.Encoder encoder = Base64.getEncoder();
            encryptedString = encoder.encodeToString(cipher.doFinal(str.getBytes()));
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            System.out.println("Something went wrong with the encryption");
        }

        return encryptedString;
    }

    /**
     * Decrypt a string with given password
     *
     * @param str string to decrypt
     * @param password password to use
     * @return decrypted string
     */
    private static String decryptString(String str, String password) {
        byte[] KeyData = password.getBytes();
        SecretKeySpec blowfish = new SecretKeySpec(KeyData, "Blowfish");
        Base64.Decoder decoder = Base64.getDecoder();
        String decryptedString = "";
        Cipher cipher = null;

        try {
            cipher = Cipher.getInstance("Blowfish");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            System.out.println("Something went wrong with the decryption or the key is wrong");
        }

        try {
            assert cipher != null;
            cipher.init(Cipher.DECRYPT_MODE, blowfish);
        } catch (InvalidKeyException e) {
            System.out.println("Invalid decryption key");
        }

        try {
            decryptedString = new String(cipher.doFinal(decoder.decode(str)));
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            System.out.println("Something went wrong with the decryption");
        }

        return decryptedString;
    }

    /**
     * Encrypt all files that end in .properties extension in /resourcses path
     *
     * @param password password to use
     */
    private static void encrypt(String password) {
        String line;
        String resourcesPath = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/";
        File dir = new File(resourcesPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".properties"));

        if (files == null || files.length == 0) {
            throw new NullPointerException("No files found that end in .properties extension");
        }

        for (File file : files) {
            try {

                String newFile = resourcesPath +
                        file.getName().replace(".properties", ".properties.pf");
                FileReader fileReader = new FileReader(file);
                FileWriter fileWriter = new FileWriter(newFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while ((line = bufferedReader.readLine()) != null) {
                    fileWriter.write(encryptString(line, password));
                    fileWriter.write(System.lineSeparator());
                }

                bufferedReader.close();
                fileWriter.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Unable to open file '" + file + "'");
            } catch (IOException ex) {
                System.out.println("Error reading file '" + file + "'");
            }
        }
    }

    /**
     * Decrypt all files that end in .pf extension in /resourcses path
     *
     * @param password password to use
     */
    private static void decrpyt(String password) {
        String line;
        String resourcesPath = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/";
        File dir = new File(resourcesPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".pf"));

        if (files == null || files.length == 0) {
            throw new NullPointerException("No files found that end in .pf extension");
        }

        for (File file : files) {
            try {
                String newFile = resourcesPath + file.getName().replace(".pf", "");
                FileReader fileReader = new FileReader(file);
                FileWriter fileWriter = new FileWriter(newFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while ((line = bufferedReader.readLine()) != null) {
                    fileWriter.write(decryptString(line, password));
                    fileWriter.write(System.lineSeparator());
                }

                bufferedReader.close();
                fileWriter.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Unable to open file '" + file + "'");
            } catch (IOException ex) {
                System.out.println("Error reading file '" + file + "'");
            }
        }
    }

    /**
     * Main entry point to start this utility.
     *
     * @param args <decrypt> or <encrypt> AND <password> argument
     */
    public static void main(String[] args) {

        String method;
        String password;

        if (args.length != 2) {
            System.out.println("You must provide <decrypt> or <encrypt> AND <password> argument");
            throw new IllegalArgumentException();
        } else {
            method = args[0].toUpperCase();
            password = args[1];
        }

        if (!(method.equals("ENCRYPT") || method.equals("DECRYPT"))) {
            System.out.println("First argument must be <decrypt> or <encrypt>");
            throw new IllegalArgumentException();
        }

        if (method.equals("ENCRYPT")) {
            encrypt(password);
        } else {
            decrpyt(password);
        }

        System.out.println("All done!");
    }
}
