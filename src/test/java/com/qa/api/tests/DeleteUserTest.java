package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojos.User;
import com.qa.api.utilities.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest {
	
	@Test
	public void deleteUserTest_TC1() {
			//1.create a user:
			User user = User.builder()
							.name("SusyCoburn")
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
			
			
			//3.DELETE: delete the same user with the above set data for the same user
			Response deleteRes = rc.deleteAPICAll("/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(deleteRes.statusCode(), 204);
			
			//4.GET: recheck the deleted user's data using his id
			Response getResAfterDelete = rc.getAPICall("/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(getResAfterDelete.statusCode(), 404);
			
			Assert.assertEquals(getResAfterDelete.jsonPath().getString("message"), "Resource not found");
			
		}
	 

}
