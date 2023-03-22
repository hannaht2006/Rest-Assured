package com.cydeo.day13;

import com.cydeo.utilities.SpartanNewTestBase;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class P01_Review extends SpartanNewTestBase {


    @Test
    public void test1(){
        given().accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .when().get("/spartans").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("id[0]", is(3877))
                .body("id[1]",is(3878));
    }

    @Test
    public void test2(){

        given().spec(reqResSpec)
                .pathParam("id", 3877)
        .when().get("/spartans/{id}")
        .then().spec(resSpec);
    }
    @Test
    public void test3(){
        given().spec(dynamicReqSpec("user", "user"))
                .pathParam("id", 3878)
                .when().get("/spartans/{id}")
                .then().spec(dynamicResSpec(200));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/rbac.csv", numLinesToSkip = 1)
    public void test4(String username, String password, int id, int statusCode){
        given().spec(dynamicReqSpec(username, password))
                .pathParam("id", id)
                .when().get("/spartans/{id}")
                .then().spec(dynamicResSpec(statusCode));
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/rbac_Delete.csv", numLinesToSkip = 1)
    public void test5(String username, String password, int id, int statusCode){
        given().spec(dynamicReqSpec(username, password))
                .pathParam("id", id)
                .when().delete("/spartans/{id}")
                .then().spec(dynamicResSpec(statusCode));
    }

}
