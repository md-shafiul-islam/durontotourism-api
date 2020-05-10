package com.usoit.api.customserializeraderializera;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomPackageKeyDeserializer extends KeyDeserializer{

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
		return mapper.readValue(key, Package.class);
	}
	
	
}
