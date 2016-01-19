package com.bitmonlab.osiris.imports.map.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cryptography {
	

	private static final String HEXES = "0123456789ABCDEF";
	private static final String ALGORITHM = "SHA-512";

	
	public static final String calculateCheckSum(final String strData) throws NoSuchAlgorithmException{ 
			
			MessageDigest messageDigest;
				
			messageDigest = MessageDigest.getInstance(ALGORITHM);
			messageDigest.reset();
			messageDigest.update(strData.getBytes(), 0, strData.length());		
								
			
			return codHex(messageDigest.digest());
									
	}
	
	
	private static String codHex(final byte[] bDigest) {
		
	    if ( bDigest == null ) {  return null; }
	    
	    final StringBuffer hex = new StringBuffer( 2 * bDigest.length );
	    
	    for (int i=0; i<bDigest.length; i++){
	    	
	      hex.append(HEXES.charAt((bDigest[i] & 0xF0) >> 4))
	         .append(HEXES.charAt((bDigest[i] & 0x0F)));
	      
	    }
	    	    
	    
	    return hex.toString();
	    
	}


}
