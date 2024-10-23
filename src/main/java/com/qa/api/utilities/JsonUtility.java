package com.qa.api.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class JsonUtility {
	
	private static ObjectMapper objmapper = new ObjectMapper();
	
	public static <T> T deSerialize(Response response, Class<T> targetClass) {
		try {
			return objmapper.readValue(response.getBody().asString(), targetClass);
		} catch (Exception e) {
		  throw new RuntimeException("deserialization failed for..."+targetClass.getName());
		}
	}

}
