package com.sck.common.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

public class ISO8601JsonDateDeserializer extends UntypedObjectDeserializer  {
	
	private static final long serialVersionUID = -9081094355684052477L;

	private List<DateFormat> dateFormats = new ArrayList<DateFormat>();
	
	private Pattern datePattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}(Z|[+-](\\d{2}:?\\d{2}))?$");
	
	public ISO8601JsonDateDeserializer()
	{
		dateFormats.add(new ISO8601DateFormat());
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
	}
	
	public synchronized Date parseDate(String date) throws ParseException {
		ParseException pe = null;
		for (DateFormat f : dateFormats) {
			try {
				return f.parse(date);
			} catch (ParseException e) {
				pe = (pe == null) ? e : pe;
			} catch (IllegalArgumentException e) {
				pe = (pe == null) ? new ParseException(e.getMessage(), 0)
						: pe;
			}
		}
		throw pe;
	}

	public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		Object result = super.deserialize(jp, ctxt);
		if (result instanceof String) {
			String string = (String) result;
			if (datePattern.matcher(string).matches()) {
				try
				{
					return parseDate(string);
				}
				catch(ParseException e)
				{
					try {
						throw e;
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		return result;
	}
	/*
	@Override
	public Object deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {
		String date = jsonparser.getText();
		
        try {
        	for (DateFormat f : dateFormats) {
        		return f.parse(date);
        	}
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
	}
	*/
}
