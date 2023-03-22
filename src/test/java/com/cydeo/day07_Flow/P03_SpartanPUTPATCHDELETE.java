package com.cydeo.day07_Flow;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class P03_SpartanPUTPATCHDELETE extends SpartanTestBase {

@DisplayName("PUT Spartan single with Map")
    @Test
public void test1(){

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("name", "Math Damon PUT");
    requestBody.put("gender","Male" );
    requestBody.put("phone",8877445596L );

 //PUT update existing record, so we choose one of the existing id. it may be different from teacher
    int id = 229;

    given().//since we are not getting response so we don't need accept(ContentType.JSON)
    log().body()
            .contentType(ContentType.JSON)//for update data with  PUT
            .pathParam("id", id)
            .body(requestBody)
            .when().put("/api/spartans/{id}")
            .then()
            .statusCode(204);//no content --> it means I did successfully but no response

    //Create a GET method with same ID to see is it updated
}

    @DisplayName("PATCH Spartan single with Map")
    @Test
    public void test2(){
//just like we did in POST we can use other option as well (String, POJO)
        Map<String, Object> requestBody = new LinkedHashMap<>();

        requestBody.put("name", "Math D PATCH");
     //if the name longer than 15, it will return 500 status code

        //PATCH to update existing record, so we choose one of the existing id. it may be different from teacher
        int id = 229;

        given().//since we are not getting response so we don't need accept(ContentType.JSON)
                log().body()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(requestBody)
                .when().patch("/api/spartans/{id}")
                .then()
                .statusCode(204);//no content --> it means I did successfully but no response

        //Create a GET method with same ID to see is it updated
    }

    @DisplayName("DELETE Spartan single with Map")
    @Test
    public void test3(){
//this test only run 1 time correctly for DELETE (because after delete, we don't have any same data with same id to delete)
        int id = 232;

        given()//since we are not getting response so we don't need accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when().delete("/api/spartans/{id}")
                .then()
                .statusCode(204);//no content --> it means I did successfully but no response

        //get 404 not found after delete
        //Create a GET method with same ID to see is it updated
        given().accept(ContentType.JSON)
                .pathParam("id", id)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(404);

        //Create a DB connection
        //Write query
        //select * from spartans
        //where spartan_id = 202;
    }
}
