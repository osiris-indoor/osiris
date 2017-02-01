package com.bitmonlab.osiris.encrypt;


import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


public class EncryptOsirisPassword 
{
    public static void main( String[] args ) throws Exception
    {
    	String password = null;
    	
    	if(args.length > 1){
    		System.out.println("There are a lot of arguments");
    		System.out.println("Example: java -jar osiris-encrypt-password.jar password");
    	}else if(args.length==0){    
    		System.out.println("You need to put password argument");
    		System.out.println("Example: java -jar osiris-encrypt-password.jar password");
    	}else{
    		password = args[0];
    		
    	}
    		    	
    	  String encriptado = encrypt(password);
          System.out.println(encriptado);
         
    }
    
 
     
    public static String encrypt(String texto) {
     
            String secretKey = "osirisSecurity"; 
            String base64EncryptedString = "";
     
            try {
     
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
                byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
     
                SecretKey key = new SecretKeySpec(keyBytes, "DESede");
                Cipher cipher = Cipher.getInstance("DESede");
                cipher.init(Cipher.ENCRYPT_MODE, key);
     
                byte[] plainTextBytes = texto.getBytes("utf-8");
                byte[] buf = cipher.doFinal(plainTextBytes);
                byte[] base64Bytes = Base64.encodeBase64(buf);
                base64EncryptedString = new String(base64Bytes);
     
            } catch (Exception ex) {
            }
            return base64EncryptedString;
        }
     
}



