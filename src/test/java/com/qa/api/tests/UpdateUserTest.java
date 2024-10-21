package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojos.User;
import com.qa.api.utilities.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest {
	
	@Test
	public void updateUserWithBuilderPatternTest_TC1() {
		
		//1.create a user:
		User user = User.builder()
						.name("SusanCoburn")
							.email(StringUtility.getRandomEmail())
								.gender("female")
									.status("inactive")
										.build();
		
		Response postRes = rc.postAPICAll("/public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(postRes.getStatusCode(), 201);
		
		//fetch user id postRes to be used for validation in the GET call:
		String userId =postRes.jsonPath().getString("id");
		System.out.println("user id captured from  POST call response:"+userId);
		
		//2.GET: the same user just gotten created
		Response getRes = rc.getAPICall("/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getRes.statusCode(), 200);
		Assert.assertEquals(getRes.jsonPath().getString("id"), userId);
		
		//update the user details using the setter:
		user.setEmail(StringUtility.getRandomEmail());
		user.setStatus("active");
		
		//3.PUT: update the same user with the above set data for the same user
		Response putRes = rc.putAPICAll("/public/v2/users/"+userId, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(putRes.statusCode(), 200);
		Assert.assertEquals(putRes.jsonPath().getString("id"), userId);
		Assert.assertEquals(putRes.jsonPath().getString("email"), user.getEmail());
		Assert.assertEquals(putRes.jsonPath().getString("status"), user.getStatus());
		
	}
 

}
