package com.qa.api.client;

import static io.restassured.RestAssured.expect;

import java.io.File;
import java.util.Map;

import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.FrameWorkException;
import com.qa.api.manager.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

public class RestClient {
	//1.setting up the request for the TC:making it private which can be accessed by
	//public methods--Encapsulation=>private vars/methods to be accessed by public fns
	
	//creating the variable base Url---1
	//set up the request spec until when()--2
	//include the header token--from the switch case statement--3
	//use one of the CRUD to hit the server with--4==>define all cruds
	//validate the response from the server--5===>define the responseSpecs for each call at top 
	//level and call them in each crud operation to validate the expected vs actual
	
	//5.define the responseSpecs:
	private ResponseSpecification resSpec200 = expect().statusCode(200);
	private ResponseSpecification resSpec200or201 = expect().statusCode(anyOf(equalTo(200), equalTo(201)));
	private ResponseSpecification resSpec200or404 = expect().statusCode(anyOf(equalTo(200), equalTo(404)));
	private ResponseSpecification resSpec201 = expect().statusCode(201);
	private ResponseSpecification resSpec204 = expect().statusCode(204);
	private ResponseSpecification resSpec400 = expect().statusCode(400);
	private ResponseSpecification resSpec401 = expect().statusCode(401);
	private ResponseSpecification resSpec404 = expect().statusCode(404);
	private ResponseSpecification resSpec422 = expect().statusCode(422);
	private ResponseSpecification resSpec500 = expect().statusCode(500);
	
	
	//1.creating the variable base Url:
//	private String baseURL = ConfigManager.getProp("baseUrl");
	
	private RequestSpecification setUpRequest(String baseUrl, AuthType authType, ContentType contentType) {
	
	//2.
    RequestSpecification reqSpec = RestAssured.given().log().all()
		             					.baseUri(baseUrl)
		             						.contentType(contentType)
		             							.accept(contentType);
    //3.
    switch (authType) {
    
	case BEARER_TOKEN:
		 reqSpec.header("Authorization", "Bearer "+ConfigManager.getProp("bearerToken"));
		 break;
	case CONTACTS_BEARER_TOKEN:
		 reqSpec.header("Authorization", "Bearer "+ConfigManager.getProp("contacts_bearer_Token"));
		 break;
	case OAUTH2:
		 reqSpec.header("Authorization", "Bearer "+generateOAuth2Token());
		 break;

	case BASIC_AUTH:
		 reqSpec.header("Authorization", "Basic ");
		 break;

	case API_KEY:
		 reqSpec.header("api_key", "Bearer "+ConfigManager.getProp("apiKey"));
		 break;

	case NO_AUTH:
		 System.out.println("Auth is not required...");
		 break;

	default:
		System.out.println("This Auth is not supported, Pls pass the right AuthType!");
		throw new FrameWorkException("NO AUTH SUPPORTED");	
	}
    return reqSpec;
	}  
	private String generateOAuth2Token() {
    	return RestAssured.given().log().all()
    	             .formParam("clientId", ConfigManager.getProp("clientId"))
    	              .formParam("clientSecret", ConfigManager.getProp("clientSecret"))
    	               .formParam("grantType", ConfigManager.getProp("grantType"))
    	                .post(ConfigManager.getProp("tokenUrl"))
    	                 .then().log().all()
    	                   .extract()
    	                     .path("access_token");
    
    }
		             		
	//********************CRUD******************************
	/**
	 * This method is used to call the GET APIs
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return it returns the get api response
	 */
	//Refactoring: setting up the base url in each of the CRUD calls and commenting step 1.
	public Response getAPICall(String baseUrl, String endPoint,Map<String, String>queryParams, Map<String, String>pathParams,
			                       AuthType authType, ContentType contentType) {
		
		RequestSpecification reqSpec = setUpRequest(baseUrl, authType, contentType);
		
		applyParamsNullCheck(reqSpec,queryParams, pathParams);

		
		Response resGET = reqSpec.get(endPoint).then().spec(resSpec200or404 ).extract().response();
		resGET.prettyPrint();
		return resGET;
	}
	/**
	 * 
	 * This method is used to call the POST APIs
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return it returns the post api response
	 */
	public <T> Response postAPICAll(String baseUrl, String endPoint, T body,Map<String, String>queryParams, 
			Map<String, String>pathParams, AuthType authType, ContentType contentType) {
		
		RequestSpecification reqSpec = setUpRequest(baseUrl, authType, contentType);
		
//		if(queryParams!=null) {
//			reqSpec.queryParams(queryParams);
//		}
//		if(pathParams!=null) {
//			reqSpec.queryParams(pathParams);
//		}
		
		//alternative for above code:
		applyParamsNullCheck(reqSpec,queryParams, pathParams);
		
		Response resPOST = reqSpec.body(body).post(endPoint).then().spec(resSpec200or201).extract().response();
		resPOST.prettyPrint();
		return resPOST;
	}
	
	//postAPICAll: OverLoaded with file Type body
	/**
	 * This method is used to call the POST APIs for the Json File Body
	 * @param endPoint
	 * @param file
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return it returns the post api response
	 */
	public Response postAPICAll(String baseUrl, String endPoint, File file,Map<String, String>queryParams, 
			Map<String, String>pathParams, AuthType authType, ContentType contentType) {
		
		RequestSpecification reqSpec = setUpRequest(baseUrl, authType, contentType);
		
		applyParamsNullCheck(reqSpec,queryParams, pathParams);
		
		Response resPOST = reqSpec.body(file).post(endPoint).then().spec(resSpec201).extract().response();
		resPOST.prettyPrint();
		return resPOST;
	}
	/**
	 * This method is used to call PUT API calls by providing the complete body
	 * @param <T>
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return it returns the put api response
	 */
	public <T> Response putAPICAll(String baseUrl, String endPoint, T body,Map<String, String>queryParams, 
			Map<String, String>pathParams, AuthType authType, ContentType contentType) {
		
		RequestSpecification reqSpec = setUpRequest(baseUrl, authType, contentType);
		
		applyParamsNullCheck(reqSpec,queryParams, pathParams);
		
		Response resPUT = reqSpec.body(body).put(endPoint).then().spec(resSpec200).extract().response();
		resPUT.prettyPrint();
		return resPUT;
	}
	/**
	 * This method is used to call a patch call API
	 * @param <T>
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return it returns the patch api response
	 */
	public <T> Response patchAPICAll(String baseUrl, String endPoint, T body,Map<String, String>queryParams, 
			Map<String, String>pathParams, AuthType authType, ContentType contentType) {
		
		RequestSpecification reqSpec = setUpRequest(baseUrl, authType, contentType);
		
		applyParamsNullCheck(reqSpec,queryParams, pathParams);
		
		Response resPATCH = reqSpec.body(body).patch(endPoint).then().spec(resSpec200).extract().response();
		resPATCH.prettyPrint();
		return resPATCH;
	}
	/**
	 * This method is used to call the Delete API call
	 * @param <T>
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return it returns the delete api response
	 */
	public  Response deleteAPICAll(String baseUrl, String endPoint, Map<String, String>queryParams, 
			Map<String, String>pathParams, AuthType authType, ContentType contentType) {
		
		RequestSpecification reqSpec = setUpRequest(baseUrl, authType, contentType);
		
		applyParamsNullCheck(reqSpec,queryParams, pathParams);
		
		Response resDELETE = reqSpec.delete(endPoint).then().spec(resSpec204).extract().response();
		resDELETE.prettyPrint();
		return resDELETE;
	}
	
	//Basic Generic Utility fn:
	private void applyParamsNullCheck(RequestSpecification reqSpec, Map<String, String>queryParams, 
			Map<String, String>pathParams) {
		
		if(queryParams!=null) {
			reqSpec.queryParams(queryParams);
		}
		if(pathParams!=null) {
			reqSpec.queryParams(pathParams);
		}
		
	}

}
