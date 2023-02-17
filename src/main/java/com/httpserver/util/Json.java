package com.httpserver.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Json {
	
	private static ObjectMapper myObjectMapper =defaultObjectMapper();
	
	private static ObjectMapper defaultObjectMapper() {
		ObjectMapper om=new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		return om;
	}
	public static JsonNode parse(String jsonSrc) throws IOException {
		return myObjectMapper.readTree(jsonSrc);
	}
	public static <E> E fromJson(JsonNode node, Class<E> clazz) throws JsonProcessingException {
		return myObjectMapper.treeToValue(node,clazz);
	}
	public static JsonNode toJson(Object obj) throws IOException {
		return myObjectMapper.valueToTree(obj);
	}
	public static String stringify(JsonNode node) throws JsonProcessingException {
		return generateJson(node,false);
	}
	public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
		return generateJson(node,true);
	}
	public static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
		ObjectWriter objectWriter=myObjectMapper.writer();
		if(pretty){
			objectWriter=objectWriter.with(SerializationFeature.INDENT_OUTPUT);
		}
		return objectWriter.writeValueAsString(o);
	}
	
	
	
	
	

}
