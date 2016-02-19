package com.gocpf.application;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomLocalDateTimeDeserializer extends JsonDeserializer<Date> {
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	 @Override
	    public Date deserialize(JsonParser jp, DeserializationContext ctxt)
	            throws IOException {
	        JsonToken t = jp.getCurrentToken();
	        if (t == JsonToken.VALUE_STRING) {
	            String str = jp.getText().trim();
	            try {
					return simpleDateFormat.parse(str);
				} catch (ParseException e) {
					e.printStackTrace();
					System.err.println(e);
				}
//	            return ISODateTimeFormat.dateTimeParser().parseLocalDate(str).toDate();
	        }
	        if (t == JsonToken.VALUE_NUMBER_INT) {
	            return new LocalDate(jp.getLongValue()).toDate();
	        }
	        throw ctxt.mappingException(handledType());
	    }




}
