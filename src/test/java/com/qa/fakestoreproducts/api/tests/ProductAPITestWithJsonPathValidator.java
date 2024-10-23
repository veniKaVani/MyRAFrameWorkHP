package com.qa.fakestoreproducts.api.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.utilities.JsonPathValidator;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITestWithJsonPathValidator extends BaseTest{
	
	@Test
	public void getProductTest() {
		
		Response getRes = rc.getAPICall(BASE_URL_FAKESTORE, AppConstants.FAKESTORE_PRODUCTS_ALL_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(getRes.statusCode(), 200);
		
		List<Number> prices = JsonPathValidator.readList(getRes, "$[?(@.price>50)].price");
		System.out.println(prices);
		
		List<Number> Ids = JsonPathValidator.readList(getRes, "$[?(@.price>50)].id");
		System.out.println(Ids);
		
		List<Number> rates = JsonPathValidator.readList(getRes, "$[?(@.price>50)].rating.rate");
		System.out.println(rates);
		
		List<Number> count = JsonPathValidator.readList(getRes, "$[?(@.price>50)].rating.count");
		System.out.println(count);
		
		//get map:
		List<Map<String, Object>> jewelCatalogue = JsonPathValidator.readListOfMaps(getRes, "$[?(@.category == 'jewelery')].['title', 'price']");
		System.out.println(jewelCatalogue.size());
		
		for(Map<String, Object>product:jewelCatalogue) {
			String title = (String)product.get("title");
			Number price = (Number)product.get("price");
			
			System.out.println("title:"+title);
			System.out.println("price:"+price);
			System.out.println("------------");
		}
		System.out.println("=================");
		//get min price:
		Double minPrice = JsonPathValidator.read(getRes, "min($[*].price)");
		System.out.println(minPrice);
		
	}

}
