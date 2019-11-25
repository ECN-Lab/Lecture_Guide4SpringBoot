package com.ntsphere.common.crypto;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256 {
	private String iv;
	private Key keySpec;

	
	
	
	
	public AES256(String key) throws Exception {
		
		key = (key + "                ").substring(0, 16);
		
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		
		
		if (len > keyBytes.length) {
			len = keyBytes.length;
		}
		
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

		this.iv = key;
		this.keySpec = keySpec;
	}


	public String encrypt(String str) throws Exception {
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
		
		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String enStr = new String(Base64Coder.encode(encrypted));
		
		return enStr;
	}


	public String decrypt(String str) throws Exception {
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
		
		byte[] byteStr = Base64Coder.decode(str.toCharArray());
		
		return new String(c.doFinal(byteStr), "UTF-8");
	}
	
	
	public static String encrypt(String key, String str) throws Exception {
		AES256 aes = new AES256(key);
		return aes.encrypt(str);
	}
	
	
	public static String decrypt(String key, String str) throws Exception {
		AES256 aes = new AES256(key);
		return aes.decrypt(str);
	}
}
