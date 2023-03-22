package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class P01_SpartanGetRequests {

    String url = "http://44.212.37.188:8000";
    /*
     * Given accept is application/json
     * When user sends GET request /api/spartans endpoint
     * Then status code should be 200
     * And Content type should be application/json
     */
  @DisplayName("Get single spartans")
  @Test
  public void getAllSpatans(){
     Response response = RestAssured
              .given()
              .accept(ContentType.JSON)
              .when().get(url + "/api/spartans");

     //print
      response.prettyPrint();

      //how to get status code
      int actualStatusCode = response.statusCode();

      Assertions.assertEquals(200, actualStatusCode);

      //how to get content type
     String actualContentType = response.contentType();
     Assertions.assertEquals("application/json", actualContentType);

     //how to get header info
     String connection = response.header("Connection");
      System.out.println(connection);

      //get content type of header
      System.out.println("response.header(\"content-Type\") = " + response.header("content-Type"));

      //can we get connection() same as contentType() insteading of using header?
      //AS: REst Assured created couple of methods for common usege
      //staustcode()  contentType() methods are specificly create by them. So there is connection method

      //get Date
      System.out.println("response.header(\"Date\") = " + response.header("Date"));

      //how can we verify date
     Boolean isDateExist = response.headers().hasHeaderWithName("Date");
     Assertions.assertTrue(isDateExist);
  }

    /*
     * Given accept is application/json
     * When user sends GET request /api/spartans/3 endpoint
     * Then status code should be 200
     * And Content type should be application/json
     * And response body needs to contains Fidole
     */

    @DisplayName("Get single spartans")
    @Test
    public void getSpartans(){

 Response response  = RestAssured
            .given().accept(ContentType.JSON)
            .when().get(url +"/api/spartans/3");

 //verify status code
   int actualStatusCode = response.statusCode();
   Assertions.assertEquals(200, actualStatusCode);

    //verify contentType is application json
    Assertions.assertEquals("application/json", response.header("Content-Type"));

    Assertions.assertEquals("application/json", response.contentType());

    Assertions.assertEquals(ContentType.JSON.toString(), response.header("Content-Type"));

    //ContentType.JSON.toString() --> it makes enum to String to be able to use in assertions

    // verify body contains Fidole
    Assertions.assertTrue(response.body().asString().contains("Fidole"));

//    Assertions.assertTrue(response.body().toString().contains("Fidole"));--> make assertion failed
    //what if we have typo while we are getting header
//if we don't have related header or if we have typo it will return NULL

    System.out.println("response.headers(\"Keep-Alive\") = " + response.header("Keep-Alive"));

    System.out.println(response.headers().getValue("Keep-Alive"));
}

    /*
            Given no headers provided
            When Users send GET request to /api/hello
            Then response status code should be 200
            And Content type header should be "text/plain;charset=UTF-8"
            And header should contain Date
            And Content-Length should be 17
            And body should be "Hello from Sparta"
        */
@DisplayName("hello")
@Test
    public void helloSpartans(){
//When Users send GET request to /api/hello
Response response = RestAssured
        .when().get(url + "/api/hello");

//hen response status code should be 200
Assertions.assertEquals(200,response.statusCode() );

//And Content type header should be "text/plain;charset=UTF-8"
Assertions.assertEquals("text/plain;charset=UTF-8", response.contentType());

//And header should contain Date
Assertions.assertTrue(response.headers().hasHeaderWithName("Date"));

//And Content-Length should be 17
    Assertions.assertEquals("17", response.header("content-Length"));

    //And body should be "Hello from Sparta"

    response.body().asString().contains("Hello from Spartans");
}



}
