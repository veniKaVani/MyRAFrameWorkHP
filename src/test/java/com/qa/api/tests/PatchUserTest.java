package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.pojos.User;
import com.qa.api.utilities.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PatchUserTest extends BaseTest{
	
	@Test
	public void updateUserWithBuilderPatternTest_TC1() {
		
		//1.create a user:
		User user = User.builder()
						.name("SusCoburn")
							.email(StringUtility.getRandomEmail())
								.gender("female")
									.status("inactive")
										.build();
		
		Response postRes = rc.postAPICAll(BASE_URL_GOREST, AppConstants.GOREST_USERS_ALL_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(postRes.getStatusCode(), 201);
		
		//fetch user id postRes to be used for validation in the GET call:
		String userId =postRes.jsonPath().getString("id");
		System.out.println("user id captured from  POST call response:"+userId);
		
		//2.GET: the same user just gotten created
		Response getRes = rc.getAPICall(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getRes.statusCode(), 200);
		Assert.assertEquals(getRes.jsonPath().getString("id"), userId);
		
		//patch the user details using the setter:
		user.setName("RobertCoburn");
		user.setGender("male");
		
		//3.PATCH: update the same user with the above set data for the same user
		Response patchRes = rc.patchAPICAll(BASE_URL_GOREST, "/public/v2/users/"+userId, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(patchRes.statusCode(), 200);
		Assert.assertEquals(patchRes.jsonPath().getString("id"), userId);
		Assert.assertEquals(patchRes.jsonPath().getString("name"), user.getName());
		Assert.assertEquals(patchRes.jsonPath().getString("gender"), user.getGender());
		
	}
 

}
