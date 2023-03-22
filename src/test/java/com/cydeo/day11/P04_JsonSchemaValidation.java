package com.cydeo.day11;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class P04_JsonSchemaValidation extends SpartanTestBase {

    @DisplayName("GET api/spartans/{id} to validate with JsonSchemaValidator")
    @Test
    public void test1(){

        given().accept(ContentType.JSON)
                .pathParam("id", 45)
                .when().get("/api/spartans/{id}").prettyPeek()
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json"));
//we get SingleSpartanSchema.json from developer for validation


        // it gets response from execution and we provided  structure of response in following path

        //   --JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json")

        // if structure that we are getting matches with SingleSpartanSchema it will pass.
    }

    @DisplayName("GET api/spartans/{id} to validate with JsonSchemaValidator")
    @Test
    public void test2(){

        given().accept(ContentType.JSON)
                .pathParam("id", 45)
                .when().get("/api/spartans/{id}").prettyPeek()
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/SingleSpartanSchema.json")));
//we get SingleSpartanSchema.json from developer for validation


        // it gets response from execution and we provided  structure of response in following path

        //   --JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json")

        // if structure that we are getting matches with SingleSpartanSchema it will pass.
    }

    @DisplayName("GET api/spartans/{id} to validate with JsonSchemaValidator")
    @Test
    public void test3(){

        given().accept(ContentType.JSON)
                .when().get("/api/spartans/search").prettyPeek()
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SearchSpartanSchema.json"));
//we get SearchSpartanSchema.json from developer for validation

    }
    /**
     * DO schema validation for ALLSPARTAN and POST SINGLE SPARTAN
     * 1. get all spartan by using /api/spartans
     * 2. validate schema by using JsonSchemaValidator
     *
     * POST SINGLE SPARTANS
     * 1. post single spartan
     * 2. validate schema by using JsonSchemaValidator
     */
    @DisplayName("Get all spartans")
    @Test
    public void test4(){
given().accept(ContentType.JSON)
        .when().get("/api/spartans")
        .then()
        .statusCode(200)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("AllSpartanSchema.json"));


    }


    @DisplayName("POST single spartan")
    @Test
    public void test5(){
        //create requestBody for POST by POJO serilization
        Spartan requestBody = new Spartan();
        requestBody.setGender("Male");
        requestBody.setName("Post Body");
        requestBody.setPhone(1234567890l);

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/api/spartans")
                .prettyPeek()
                .then()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SpartanPostSchema.json"));



    }
}
