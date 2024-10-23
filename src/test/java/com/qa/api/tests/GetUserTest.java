package com.qa.api.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserTest extends BaseTest{
	
	@Test
	public void getUsersTest_TC1() {
	    Map<String, String> queryParams = new HashMap<String, String>();
	    queryParams.put("name", "Trivedi");
	    queryParams.put("status", "active");
	    
		Response response = rc.getAPICall(BASE_URL_GOREST,AppConstants.GOREST_USERS_ALL_ENDPOINT, queryParams, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test
	public void getSingleUserTest_TC2() {
	    
		Response response = rc.getAPICall(BASE_URL_GOREST, "/public/v2/users/7481165", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	

}
