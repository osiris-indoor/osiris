package com.bitmonlab.osiris.imports.map.utils;

import java.security.MessageDigest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bitmonlab.osiris.imports.map.utils.Cryptography;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageDigest.class})
public class CryptographyTest {
	
	@Mock
	private MessageDigest messageDigest;
	
	@Mock
	private StringBuffer hex;
	
	@Mock
	private String strData;
	
	@Mock
	private String strChkSum;
	
	
	@Test
	public void calculateCheckSum() throws Exception{
		
		
		final String HEXES = "0123456789ABCDEF";
		final String ALGORITHM = "SHA-512";
		int sizeBuffer = 100;		
		byte[] bDigest=new byte[sizeBuffer];
		
		 //Fixture
		 PowerMockito.mockStatic(MessageDigest.class);
		 PowerMockito.when(MessageDigest.getInstance(ALGORITHM)).thenReturn(messageDigest);				
		 Mockito.when(messageDigest.digest()).thenReturn(bDigest);
		 PowerMockito.whenNew(StringBuffer.class).withArguments(sizeBuffer).thenReturn(hex);
		 Mockito.when(hex.append(HEXES.charAt((bDigest[0] & 0xF0) >> 4))).thenReturn(hex);
		 Mockito.when(hex.append(HEXES.charAt((bDigest[0] & 0x0F)))).thenReturn(hex);		
		 Mockito.when(hex.toString()).thenReturn(strChkSum);
		 
		 //Experimentation
		 Cryptography.calculateCheckSum(strData);
		 		 
		 //Expectation			
		 //verify(messageDigest).reset();
		 //verify(messageDigest).update(strData.getBytes(), 0, strData.length());
		 
		
	}
		

}
