package com.qa.fakestoreproducts.api.tests;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.pojos.FakeProduct;
import com.qa.api.utilities.JsonUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductApiTestUsingDeserialization extends BaseTest{
	
	@Test
	public void getAllFakeProdsTest() {
		Response getRes = rc.getAPICall(BASE_URL_FAKESTORE, AppConstants.FAKESTORE_PRODUCTS_ALL_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(getRes.statusCode(), 200, "TC PASSED");
		FakeProduct[] fp = JsonUtility.deSerialize(getRes, FakeProduct[].class);
		
		System.out.println(Arrays.toString(fp));
		
		for(FakeProduct p : fp) {
			System.out.println("ID : "+p.getId());
			System.out.println("title : "+p.getTitle());
			System.out.println("price : "+p.getPrice());
			System.out.println("rate : "+p.getRating().getRate());
			System.out.println("count : "+p.getRating().getCount());
	
		    System.out.println("---------------------------------");
		}
	}

}
