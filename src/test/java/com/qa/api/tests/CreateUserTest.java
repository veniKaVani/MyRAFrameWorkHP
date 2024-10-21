package com.qa.api.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojos.User;
import com.qa.api.utilities.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest {
	
	@Test
	public void createUserTest_TC1() {
		
		User user = new User("AdeAkintide",StringUtility.getRandomEmail(),"male","active");
		
		Response response = rc.postAPICAll("/public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
	}
	//each TC has its own workflow=>independent=>create id, get same id,
	@Test
	public void createUserWithBuilderPatternTest_TC2() {
		User user = User.builder()
						.name("SusanCoburn")
							.email(StringUtility.getRandomEmail())
								.gender("female")
									.status("inactive")
										.build();
		
		Response response = rc.postAPICAll("/public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
		
		//fetch user id to be used for validation in the GET call:
		String userId =response.jsonPath().getString("id");
		System.out.println("user id captured from  POST call response:"+userId);
		
		//GET:
		Response getRes = rc.getAPICall("/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getRes.statusCode(), 200);
		Assert.assertEquals(getRes.jsonPath().getString("id"), userId);
		Assert.assertEquals(getRes.jsonPath().getString("name"), user.getName());
		Assert.assertEquals(getRes.jsonPath().getString("email"), user.getEmail());
		
	}
 
	@Test(enabled = false)
	public void createUserUsingJsonFileTest_TC3() {
		
		File file = new File("./src\\test\\resources\\jsons\\gorestuser.json");
		
		Response response = rc.postAPICAll("/public/v2/users", file, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
	}
}
