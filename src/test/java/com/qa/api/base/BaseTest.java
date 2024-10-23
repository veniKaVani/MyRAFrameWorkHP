package com.qa.api.base;

import org.testng.annotations.BeforeTest;

import com.qa.api.client.RestClient;

public class BaseTest {
	
	protected final static String BASE_URL_REQ_RES = "https://reqres.in/api";
	protected final static String BASE_URL_GOREST = "https://gorest.co.in";
	protected final static String BASE_URL_CONTACTS = "https://thinking-tester-contact-list.herokuapp.com";
	protected final static String BASE_URL_RESTFUL_BOOKER = "https://restful-booker.herokuapp.com";
	protected final static String BASE_URL_FAKESTORE = "https://fakestoreapi.com";
	protected final static String BASE_URL_CIRCUIT = "https://ergast.com";
	
	protected RestClient rc;
	
	@BeforeTest
	public void setup() {	    
		rc = new RestClient();
	}

}
