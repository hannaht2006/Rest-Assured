package com.cydeo.day03;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class P01_SpartanWithPathParam extends SpartanTestBase {
    /*   Given accept type is Json
              And Id parameter value is 24
              When user sends GET request to /api/spartans/{id}
              Then response status code should be 200
              And response content-type: application/json
              And "Blythe" should be in response payload(body)
           */
    @DisplayName("Get spartan with path param{id} 24")

    @Test
    public void task1(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .and() //Syntactic sugar --> to increase readablikity of code we can use in our code
                .pathParam("id", 24)
                .when().get("/api/spartans/{id}");

        response.prettyPrint();

 //Then response status code should be 200

        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());//HttpStatus.SC_OK is the same with 200

 // And response content-type: application/json
        Assertions.assertEquals("application/json", response.contentType());
        Assertions.assertEquals(ContentType.JSON.toString(), response.contentType());//ContentType.JSON.toString() is the same as "application/json"

  //And "Julio" should be in response payload(body)
  Assertions.assertTrue(response.body().asString().contains("Julio"));

//instead of param name and value inside
    }

/*
        TASK
        Given accept type is Json
        And Id parameter value is 500
        When user sends GET request to /api/spartans/{id}
        Then response status code should be 404
        And response content-type: application/json
        And "Not Found" message should be in response payload
     */
@DisplayName("get spartan/api/spartans/{id} with invalid id")
    @Test
    public void task2(){
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 500)
                .when().get("/api/spartans/{id}");

        //Then response status code should be 404
   assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());

        //And response content-type: application/json
        assertEquals(ContentType.JSON.toString(), response.contentType());

        //And "Not Found" message should be in response payload
   assertTrue(response.body().asString().contains("Not Found"));
    }
    /*
           Given Accept type is Json
           And query parameter values are:
           gender|Female
           nameContains|e
           When user sends GET request to /api/spartans/search
           Then response status code should be 200
           And response content-type: application/json
           And "Female" should be in response payload
           And "Janette" should be in response payload
        */
    @DisplayName("Get request /api/spartans/search with Query Param")
    @Test
    public void test3() {



        Response response = given().accept(ContentType.JSON)
                .and()
                .queryParam("gender", "Female")
                .and()
                .queryParam("nameContains", "e")
                .when().get("/api/spartans/search");

//Then response status code should be 200
        assertEquals(HttpStatus.SC_OK, response.statusCode());

//And response content-type: application/json
        assertEquals(ContentType.JSON.toString(), response.contentType());

// And "Female" should be in response payload
       assertTrue(response.body().asString().contains("Female"));
// And "Janette" should be in response payload
        assertTrue(response.body().asString().contains("Janette"));
    }


    @DisplayName("Get request /api/spartans/search with Query Param as Map")
    @Test
    public void test4() {
        //data as a Map:
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("gender", "Female");
        queryMap.put("nameContains", "e");

        Response response = given().accept(ContentType.JSON)
                .and()
                .queryParams(queryMap)//we use Map --> need s at queryParams
                .when().get("/api/spartans/search");

//Then response status code should be 200
        assertEquals(HttpStatus.SC_OK, response.statusCode());

//And response content-type: application/json
        assertEquals(ContentType.JSON.toString(), response.contentType());

// And "Female" should be in response payload
        assertTrue(response.body().asString().contains("Female"));
// And "Janette" should be in response payload
        assertTrue(response.body().asString().contains("Janette"));
    }

}
