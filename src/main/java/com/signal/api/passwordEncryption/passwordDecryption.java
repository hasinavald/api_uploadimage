package com.signal.api.passwordEncryption;

import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class passwordDecryption {
	
	private static final String SECRET_KEY = "key";
	  private static final String SALT = "signal";
	 
	  public static String decrypt(String strToDecrypt) {
	    try {
	      byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	      IvParameterSpec ivspec = new IvParameterSpec(iv);
	 
	      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	      KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
	      SecretKey tmp = factory.generateSecret(spec);
	      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
	 
	      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
	      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	    } catch (Exception e) {
	      System.out.println("Error while decrypting: " + e.toString());
	    }
	    return null;
	  }

}
