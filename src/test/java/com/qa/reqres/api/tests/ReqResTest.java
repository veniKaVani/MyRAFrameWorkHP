package com.qa.reqres.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ReqResTest extends BaseTest {
	
	@Test
	public void getUserTest_TC1() {
	    
		Response response = rc.getAPICall(BASE_URL_REQ_RES, AppConstants.REQ_RES_ALL_USERS_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.JSON);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}

}
