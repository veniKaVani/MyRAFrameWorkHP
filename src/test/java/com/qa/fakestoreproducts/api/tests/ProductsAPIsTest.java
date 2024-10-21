package com.qa.fakestoreproducts.api.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductsAPIsTest extends BaseTest{
	
	@Test
	public void getFakeProductsTest_TC1() {
		
		Response getRes = rc.getAPICall("/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(getRes.statusCode(), 200);
		
	}
	
	@Test
	public void getSingleFakeProductTest_TC2HW() {
		
		Response getRes = rc.getAPICall("/products/1", null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(getRes.statusCode(), 200);
		
	}
	
	@Test
	public void getFakeProducts_Limit5Test_TC3() {
		Map<String, String> queryParam = Map.of("limit", "5");
		
		Response getRes = rc.getAPICall("/products", queryParam, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(getRes.statusCode(), 200);
		
	}
	
	@Test
	public void getFakeProducts_SortedDescTest_TC4HW() {
		Map<String, String> queryParam = Map.of("sort", "desc");
		
		Response getRes = rc.getAPICall("/products", queryParam, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(getRes.statusCode(), 200);
		
	}

}
