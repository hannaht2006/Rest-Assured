package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class P02_NegativeSpartansTests {

 @BeforeAll
 public static void init() {

     RestAssured.baseURI = "http://44.212.37.188:8000";
 }

     /*
      * Given accept is application/json
      * When user sends GET request /api/spartans endpoint
      * Then status code should be 200
      * And Content type should be application/json
      */
     @DisplayName("Get All spartans")

     @Test
     public void getAllSpatans(){
         Response response = given()
                 .accept(ContentType.JSON)
                 .when().get( "/api/spartans");

         //print
         response.prettyPrint();

         //how to get status code
         int actualStatusCode = response.statusCode();

         assertEquals(200, actualStatusCode);

         //how to get content type
         String actualContentType = response.contentType();
         assertEquals("application/json", actualContentType);

         //how to get header info
         String connection = response.header("Connection");
         System.out.println(connection);

         //get content type of header
         System.out.println("response.header(\"content-Type\") = " + response.header("content-Type"));

         //can we get connection() same as contentType() instead of using header?
         //AS: REst Assured created couple of methods for common usage
         //statuscode()  contentType() methods are specificly create by them. So there is connection method

         //get Date
         System.out.println("response.header(\"Date\") = " + response.header("Date"));

         //how can we verify date
         Boolean isDateExist = response.headers().hasHeaderWithName("Date");
         Assertions.assertTrue(isDateExist);
     }
/*
Given accept type application/xml
When send Get request to api/spartans/10 end point
then status code must be 406
and response content type must be
 */

     @Test
    public void test2(){

         Response response = given()
                 .accept(ContentType.XML)
                 .when().get("/api/spartans/10");

         assertEquals(406, response.statusCode());
         assertEquals("text/plain;charset=UTF-8", response.contentType());
     }

 }


