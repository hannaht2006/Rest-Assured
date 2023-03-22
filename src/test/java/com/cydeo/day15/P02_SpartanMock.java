package com.cydeo.day15;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class P02_SpartanMock extends SpartanTestBase {

    //https://b0abaf82-b4e4-46e4-8803-65e4c3edf4a5.mock.pstmn.io
   /*
    @BeforeAll
    public static void init(){
       baseURI="https://b0abaf82-b4e4-46e4-8803-65e4c3edf4a5.mock.pstmn.io";
    }
*/
    @DisplayName("GET /api/hello")
    @Test
    public void test1(){
        Response response = given().log().all()
                .accept(ContentType.TEXT)
                .when().get("/api/hello")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract().response();

        //Hello From Sparta
        Assertions.assertEquals("Hello from Sparta", response.asString());

//        log.info("GET Spartan --> " + response.asString());
    }

    @DisplayName("GET /api/spartans")
    @Test
    public void test2(){

        Response response = given().log().all().accept(ContentType.JSON)
                .when().get("/api/spartans")
                .then().statusCode(200)
                .body("id", everyItem(notNullValue()))
                .contentType(ContentType.JSON)
                .extract().response();

//log.info("GET Spartan --> " + response.asString());

        //check status code
        //check each spartan has id

    }

    @DisplayName("POST /api/spartans")
    @Test
    public void test3(){

        Map<String, Object> spartanPost = new HashMap<>();
        spartanPost.put("name", "Mike Smith" );
        spartanPost.put("gender", "Male");
        spartanPost.put("phone", 1234567890);

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(spartanPost)
                .when().post("/api/spartans")
                .prettyPeek()
                .then().statusCode(201)
                .body("data.id", notNullValue());
        //check data.id has notNullValue
        //check success message is A Spartan is Born!
    }
}
