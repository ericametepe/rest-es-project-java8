package com.gocpf.application;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomLocalDateTimeSerializer extends JsonSerializer<Date> {
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	
	  @Override
	    public void serialize(Date value, JsonGenerator generator,
	                          SerializerProvider serializerProvider)
	            throws IOException {
	        generator.writeString(simpleDateFormat.format(value));
	    }


}
