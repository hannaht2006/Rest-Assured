package com.cydeo._day05_HamCrest;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P02_Review extends SpartanTestBase {


    @Test
    public void test1(){
      given().accept(ContentType.JSON)
              .pathParam("id", 1878)
              .when().get("/api/spartans/{id}")
              .then().statusCode(200)
              .contentType(ContentType.JSON)
              .body("name", is("Thayne"))
              .body("phone", is(8852740523L));
    }

    @Test
    public void test2(){
        List<String> name = Arrays.asList("Alexander", "Bruce", "David", "Valli", "Diana");

        given().accept(ContentType.JSON)
                .queryParam("q","{\"job_id\": \"IT_PROG\"")
                .when().get("/employees")
                .then().statusCode(200)
                .body("items.manager_id", everyItem(notNullValue()))
                .body("items.job_id", everyItem(equalTo("IT_PROG")))
                .body("items.salary", everyItem(greaterThan(3000)))
                .body("items.name", equalTo(name));
    }
    @Test
    public void test3(){
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 10)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();

        Map<String, Object> spatanMap = response.as(Map.class);

         int id = (int)spatanMap.get("id"); //have to cast because response return object
         String name = (String)spatanMap.get("name");

        JsonPath js = response.jsonPath();
       Map<String, Object> spartanJs = js.getMap("");
       int id1 = (int)spartanJs.get("id");
       String gender = (String)spartanJs.get("gender");
    }
}
