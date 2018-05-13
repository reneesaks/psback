package com.professionalstrangers.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
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
    private static String encryptString(String str, String password, String salt) {

        try {
            // Derive the key, given password and salt.
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            // Encrypt the string.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            byte[] cipherText = cipher.doFinal(str.getBytes("UTF-8"));

            return Base64.getUrlEncoder().encodeToString(cipherText) +
                    ":::" + Base64.getUrlEncoder().encodeToString(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypt a string with given password
     *
     * @param str string to decrypt
     * @param password password to use
     * @return decrypted string
     */
    private static String decryptString(String str, String password, String salt) {

        try {
            // Split line
            String[] parts = str.split(":::");
            byte[] cipherText = Base64.getUrlDecoder().decode(parts[0]);
            byte[] iv = Base64.getUrlDecoder().decode(parts[1]);

            // Derive the key, given password and salt.
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            // Decrypt the message, given derived key and initialization vector.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));

            return new String(cipher.doFinal(cipherText), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Encrypt all files that end in .properties extension in /resourcses path
     *
     * @param password password to use
     */
    private static void encrypt(String password, String salt) {
        String line;
        String resourcesPath = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/";
        File dir = new File(resourcesPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".properties"));

        if (files == null || files.length == 0) {
            throw new NullPointerException("No files found that end in .properties extension");
        }

        String anim = "|/-\\";
        for (File file : files) {
            try {

                String newFile = resourcesPath +
                        file.getName().replace(".properties", ".properties.pf");
                FileReader fileReader = new FileReader(file);
                FileWriter fileWriter = new FileWriter(newFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                int counter = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    String encryptedString = encryptString(line, password, salt);
                    String data = "\r" + anim.charAt(counter++ % anim.length());

                    try {
                        System.out.write(data.getBytes());
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (encryptedString != null) {
                        fileWriter.write(encryptedString);
                        fileWriter.write(System.lineSeparator());
                    }
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
    private static void decrpyt(String password, String salt) {
        String line;
        String resourcesPath = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/";
        File dir = new File(resourcesPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".pf"));

        if (files == null || files.length == 0) {
            throw new NullPointerException("No files found that end in .pf extension");
        }

        String anim = "|/-\\";
        for (File file : files) {
            try {
                String newFile = resourcesPath + file.getName().replace(".pf", "");
                FileReader fileReader = new FileReader(file);
                FileWriter fileWriter = new FileWriter(newFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                int counter = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    String decryptedString = decryptString(line, password, salt);
                    String data = "\r" + anim.charAt(counter++ % anim.length());

                    try {
                        System.out.write(data.getBytes());
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (decryptedString != null) {
                        fileWriter.write(decryptedString);
                        fileWriter.write(System.lineSeparator());
                    }
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
     * @param args decrypt or encrypt AND password argument.
     */
    public static void main(String[] args) {

        String method;
        String password;
        String salt;

        if (args.length != 3) {
            System.out.println("You must provide <decrypt> OR <encrypt> AND <password> AND <salt> argument");
            throw new IllegalArgumentException();
        } else {
            method = args[0].toUpperCase();
            password = args[1];
            salt = args[2];
        }

        if (!(method.equals("ENCRYPT") || method.equals("DECRYPT"))) {
            System.out.println("First argument must be <decrypt> or <encrypt>");
            throw new IllegalArgumentException();
        }

        System.out.println("Working... ");


        if (method.equals("ENCRYPT")) {
            encrypt(password, salt);
        } else {
            decrpyt(password, salt);
        }

        System.out.println("\nAll done!");
    }
}
