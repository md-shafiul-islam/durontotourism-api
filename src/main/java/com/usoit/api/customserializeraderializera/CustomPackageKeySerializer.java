package com.usoit.api.customserializeraderializera;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomPackageKeySerializer extends JsonSerializer<Package>{

	private static ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void serialize(Package value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeFieldName(mapper.writeValueAsString(value));
		
	}

}
