package com.movie.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
@Component
@PropertySource("classpath:KEY.properties")
public class AesUtils {
    @Value("${AES_KEY}")
    private String aesKey;
    public String decrypt(String encryptedText) {
        try {
            byte[] cipherData = Base64.getDecoder().decode(encryptedText);

            byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

            MessageDigest md5 = MessageDigest.getInstance("MD5");

            final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1,
                    saltData, aesKey.getBytes(StandardCharsets.UTF_8),
                    md5);

            SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");

            IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

            byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);

            Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");

            aesCBC.init(Cipher.DECRYPT_MODE, key, iv);

            byte[] decryptedData = aesCBC.doFinal(encrypted);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

        int digestLength = md.getDigestLength();

        int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;

        byte[] generatedData = new byte[requiredLength];

        int generatedLength = 0;

        try {

            md.reset();
            while (generatedLength < keyLength + ivLength) {
                if (generatedLength > 0)
                    md.update(generatedData, generatedLength - digestLength, digestLength);
                md.update(password);
                if (salt != null)
                    md.update(salt, 0, 8);
                md.digest(generatedData, generatedLength, digestLength);
                for (int i = 1; i < iterations; i++) {
                    md.update(generatedData, generatedLength, digestLength);
                    md.digest(generatedData, generatedLength, digestLength);
                }
                generatedLength += digestLength;
            }

            byte[][] result = new byte[2][];

            result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);

            if (ivLength > 0)
                result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);
            return result;
        } catch (DigestException e) {
            throw new RuntimeException(e);
        } finally {
            Arrays.fill(generatedData, (byte) 0);
        }

    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();//获取公钥
        System.out.println("公钥: " + java.util.Base64.getEncoder().encodeToString(publicKey.getEncoded()));

        PrivateKey privateKey = keyPair.getPrivate();//获取私钥
        System.out.println("私钥: " + java.util.Base64.getEncoder().encodeToString(privateKey.getEncoded()));

        String plaintext = "Hello, world!";
        String encrypt = encrypt(plaintext, publicKey);
        System.out.println("加密后: " + encrypt);

        String decrypt = decrypt(encrypt, privateKey);
        System.out.println("解密后: " + decrypt);

    }
    private static final String ALGORITHM = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }
    public static String encrypt(String plaintext, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
