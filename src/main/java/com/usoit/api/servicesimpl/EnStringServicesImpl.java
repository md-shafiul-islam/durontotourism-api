package com.usoit.api.servicesimpl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.usoit.api.services.EnStringServices;

import net.bytebuddy.utility.RandomString;

@Service
public class EnStringServicesImpl implements EnStringServices{

	private String slt = "sa34";
	
	
	@Override
	public String getEncData(String stData) {
		
		System.out.println("Given String: " + stData);
		
		return getEncriptedString(stData);
	}
	

	public String genRanString(int lenght) {
		
		RandomString randomString = new RandomString(lenght);
		return randomString.nextString();
	}

	public String decodeData(String encodeString) {
		return new String(Base64.decodeBase64(encodeString.getBytes()));
	}

	public String getEncodeString(String stringData) {
		byte[] fByte = Base64.encodeBase64(stringData.getBytes());
		return new String(fByte);
	}

	public String getEncriptedString(String nMsg) {
		String genHas = null;
		
		nMsg = slt+nMsg;
		
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] mdByte = messageDigest.digest(nMsg.getBytes(StandardCharsets.UTF_8));
			
			StringBuilder stringBuilder = new StringBuilder();
			
			for(int i =0; i < mdByte.length; i++) {
				stringBuilder.append(Integer.toString((mdByte[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			genHas = stringBuilder.toString();
			
			System.out.println("Has String:  " + genHas);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return genHas;
	}
	
}
