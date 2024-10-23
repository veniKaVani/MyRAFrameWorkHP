package com.qa.api.exercisetests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.utilities.JsonPathValidator;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class JsonValidatorFunctionHWTests extends BaseTest {
	
	@Test
	public void getFakeProductsTests() {
		Response getRes = rc.getAPICall(BASE_URL_FAKESTORE, AppConstants.FAKESTORE_PRODUCTS_ALL_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(getRes.statusCode(), 200);
		
		Double maxPrice = JsonPathValidator.read(getRes, "max($[*].price)");
		System.out.println(maxPrice);
		
		Double avgPrice = JsonPathValidator.read(getRes, "avg($[*].price)");
		System.out.println(avgPrice);
		
		Double stddevPrice = JsonPathValidator.read(getRes, "stddev($[*].price)");
		System.out.println(stddevPrice);
		
		Double sumPrice = JsonPathValidator.read(getRes, "sum($[*].price)");
		System.out.println(sumPrice);
	}

}
