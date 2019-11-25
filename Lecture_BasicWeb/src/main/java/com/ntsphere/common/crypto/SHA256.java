package com.ntsphere.common.crypto;

import java.security.MessageDigest;

public class SHA256 {
	public static String encode(String pwd) {
		String encrypted = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(new String(pwd).getBytes("UTF-8"));

			byte[] digested = md.digest();
			encrypted = new String(Base64Coder.encode(digested));
		} catch (Exception e) {
			encrypted = new String(pwd);
		}

		return encrypted;
	}
}
