package com.usoit.api.data.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	private final Random RANDOM = new SecureRandom();
	
	private final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private final int ITERATIONS = 10000;
	private final int KEY_LENGHT = 256;
	
	
	public String generatePublicID(int lenght) {
		
		return genaretRandomeString(lenght);
	}

	private String genaretRandomeString(int lenght) {

		StringBuilder returnValue = new StringBuilder(lenght);
		
		for (int i = 0; i < lenght; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		
		return new String(returnValue);
	}
	
}
