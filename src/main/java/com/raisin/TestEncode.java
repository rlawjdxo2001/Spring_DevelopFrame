package com.raisin;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.Cipher;

public class TestEncode {
	    static final int KEY_SIZE = 2048;

	    public void test(String[] args) {
	        HashMap<String, String> rsaKeyPair = createKeypairAsString();
	        String publicKey = rsaKeyPair.get("publicKey");
	        String privateKey = rsaKeyPair.get("privateKey");

	        String plainText = "test";

	        String encryptedText = encode(plainText, publicKey);

	        String decryptedText = decode(encryptedText, privateKey);
	    }

	    /**
	     * 키페어 생성
	     */
	    static HashMap<String, String> createKeypairAsString() {
	        HashMap<String, String> stringKeypair = new HashMap<>();
	        try {
	            //SecureRandom secureRandom = new SecureRandom();
	            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	            keyPairGenerator.initialize(KEY_SIZE);
	            KeyPair keyPair = keyPairGenerator.genKeyPair();

	            PublicKey publicKey = keyPair.getPublic();
	            PrivateKey privateKey = keyPair.getPrivate();

	            String stringPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
	            String stringPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

	            stringKeypair.put("publicKey", stringPublicKey);
	            stringKeypair.put("privateKey", stringPrivateKey);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return stringKeypair;
	    }

	    static String encode(String plainData, String stringPublicKey) {
	        String encryptedData = null;
	        try {
	            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	            byte[] bytePublicKey = Base64.getDecoder().decode(stringPublicKey.getBytes());
	            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytePublicKey);
	            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

	            Cipher cipher = Cipher.getInstance("RSA");
	            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

	            byte[] byteEncryptedData = cipher.doFinal(plainData.getBytes());
	            encryptedData = Base64.getEncoder().encodeToString(byteEncryptedData);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return encryptedData;
	    }

	    static String decode(String encryptedData, String stringPrivateKey) {
	        String decryptedData = null;
	        try {
	            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	            byte[] bytePrivateKey = Base64.getDecoder().decode(stringPrivateKey.getBytes());
	            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytePrivateKey);
	            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

	            Cipher cipher = Cipher.getInstance("RSA");
	            cipher.init(Cipher.DECRYPT_MODE, privateKey);

	            byte[] byteEncryptedData = Base64.getDecoder().decode(encryptedData.getBytes());
	            byte[] byteDecryptedData = cipher.doFinal(byteEncryptedData);
	            decryptedData = new String(byteDecryptedData);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return decryptedData;
	    }
}
