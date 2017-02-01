package com.bitmonlab.osiris.commons.model.security;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.data.annotation.Id;

public class BasicAuth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7103315600939610068L;
	
	@Id private String _id;
	private String password;
	
		
	public BasicAuth() {
	}
	public BasicAuth(String _id) {
		this._id = _id;
	}
	
	
	
	public String getPassword() throws Exception {		
		return decryptPass();
	}
	public void setPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		this.password = encryptPass(password);
		
	}
	@Override
	public String toString() {
		return _id;
	}

	public String get_id() {
		return _id;
	}
	public void setUsername(String _id) {
		this._id = _id;
	}
	
	private String encryptPass(String pass) {
		 
	    String secretKey = "osirisSecurity"; 
	    String base64EncryptedString = "";

	    try {

	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
	        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

	        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
	        Cipher cipher = Cipher.getInstance("DESede");
	        cipher.init(Cipher.ENCRYPT_MODE, key);

	        byte[] plainTextBytes = pass.getBytes("utf-8");
	        byte[] buf = cipher.doFinal(plainTextBytes);
	        byte[] base64Bytes = Base64.encodeBase64(buf);
	        base64EncryptedString = new String(base64Bytes);

	    } catch (Exception ex) {
	    }
	    return base64EncryptedString;
	}

	private String decryptPass() throws Exception {
		 
	    String secretKey = "osirisSecurity"; 
	    String base64EncryptedString = "";

	    try {
	        byte[] message = Base64.decodeBase64(password.getBytes("utf-8"));
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
	        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
	        SecretKey key = new SecretKeySpec(keyBytes, "DESede");

	        Cipher decipher = Cipher.getInstance("DESede");
	        decipher.init(Cipher.DECRYPT_MODE, key);

	        byte[] plainText = decipher.doFinal(message);

	        base64EncryptedString = new String(plainText, "UTF-8");

	    } catch (Exception ex) {
	    }
	    return base64EncryptedString;
	}
	
}