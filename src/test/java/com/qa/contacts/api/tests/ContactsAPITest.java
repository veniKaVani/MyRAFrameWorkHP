package com.qa.contacts.api.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojos.ContactsCredentials;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactsAPITest extends BaseTest{
	
	private String tokenId;
	
	@BeforeMethod
	public void getToken() {
		ContactsCredentials creds = ContactsCredentials.builder()
				                     .email("bkarnam18@gmail.com")
				                     .password("ThummiAaku@24")
				                     .build();
		
		Response response = rc.postAPICAll("/users/login", creds, null, null, AuthType.NO_AUTH, ContentType.JSON);
		tokenId = response.jsonPath().getString("token");
		System.out.println("token generated===>"+tokenId);
		ConfigManager.setProp("contacts_bearer_Token", tokenId);
	}
	
	@Test
	public void getAllContactsTest() {
		
		Response getRes = rc.getAPICall("/contacts", null, null, AuthType.CONTACTS_BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getRes.statusCode(), 200);
	}

	
	
	
}
