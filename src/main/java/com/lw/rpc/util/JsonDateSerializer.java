package com.lw.rpc.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化日期
 *
 */
public class JsonDateSerializer extends JsonSerializer<Date> {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException {

		String formattedDate = dateFormat.format(date);

		gen.writeString(formattedDate);
	}
}
