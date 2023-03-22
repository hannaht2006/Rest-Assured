package com.cydeo.day01;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class P01_SimpleGetRequest {

    String url = "http://3.85.103.221:8000/api/spartans";

    String spartansBaseUrl = "http://3.85.103.221:8000";

    /**
     * when users send request to /api/spatans endpoint
     * then user should be able to see status code is 200
     * and print out response body into screen
     */

    @Test
    public void simpleGetRequest(){
//send request to url and get response as response interface
         Response response = RestAssured.get(url);

  // both are the same, no different. we are gonna use to assert
        System.out.println("response.getStatusCode() = " + response.getStatusCode());

        System.out.println("response.statusCode() = " + response.statusCode());

  //it gives all status line
        System.out.println("response.statusLine() = " + response.statusLine());

        int actualStatusCode = response.getStatusCode();

        Assertions.assertEquals(200, actualStatusCode);

        //How to print the screen?
        System.out.println("response.asPrettyString() = " + response.asPrettyString());
    }
    @Test
    public void viewSpartans(){
       Response response = RestAssured.get(spartansBaseUrl + "/api/spartans");

       //print status code
        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.body().prettyPrint() = " + response.body().prettyPrint());

    }

    @Test
    public void viewSpartansTest2(){
        Response response = RestAssured.get(spartansBaseUrl + "/api/spartans");
   //verify status code is 200
        Assertions.assertEquals(200, response.statusCode());

   //verify body contains Allen
   Assertions.assertTrue(response.body().asString().contains("Allen"));
    }

    @Test
    public void viewSpartansTest3(){
Response response= RestAssured.given().accept(ContentType.JSON)
        .when().get(spartansBaseUrl + "/api/spartans");

Assertions.assertEquals(200,response.statusCode() );

//verify body json
Assertions.assertEquals("application/json",response.contentType());
    }
}
