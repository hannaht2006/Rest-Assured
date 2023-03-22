package com.cydeo._day05_HamCrest;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P03_HamCrestHR extends HrTestBase {

    /*
                Given accept type is Json
                And parameters: q = {"job_id":"IT_PROG"}
                When users sends a GET request to "/employees"
                Then status code is 200
                And Content type is application/json
                Verify
                    - each employees has manager
                    - each employees working as IT_PROG
                    - each of them getting salary greater than 3000
                    - first names are .... (find proper method to check list against list)
                    - emails without checking order (provide emails in different order,just make sure it has same emails)
           */
    @Test
    public void test1(){
        /*
        email
         AHUNOLD
         BERNST
         DAUSTIN
         VPATABAL
         DLORENTZ
         */
       // firstname list:
        List<String> names = Arrays.asList("Alexander", "Bruce", "David", "Valli", "Diana");

        given().accept(ContentType.JSON)
                .queryParam("q","{\"job_id\":\"IT_PROG\"}")
          .when().get("/employees")
                .prettyPeek()  //print out what we are getting as a response,but also return response so we can continue chaining
          .then()
                .statusCode(200)
                .contentType("application/json")
                .body("items.manager_id", everyItem(notNullValue()))
                .body("items.job_id", everyItem(equalTo("IT_PROG")))
                .body("items.salary",everyItem(greaterThan(3000)))
                .body("items.first_name",equalTo(names))//names is List above
                .body("items.email",containsInAnyOrder("DAUSTIN","AHUNOLD","BERNST","VPATABAL","DLORENTZ")) ;

    }


    /*
      Given
               accept type is application/json
       When
               user sends get request to /regions
       Then
               response status code must be 200
               verify Date has values
               first region name is Europe
               first region id is 1
               four regions we have
               region names are not null
               Regions name should be same order as "Europe","Americas","Asia","Middle East and Africa"
               region ids needs to be 1,2,3,4

               print all the regions names
               ...
               ..
               .
    */

    @Test
    public void test2(){

        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and()
                .when().get("/regions").prettyPeek()
                .then()
                .statusCode(200)
                .and()
                .header("Date", is(notNullValue()))
                .body("items[0].region_name", is("Europe"))
                .body("items[0].region_id", is(1))
                .body("items", hasSize(4))
                .body("items.region_name", everyItem(notNullValue()))
                .body("items.region_name", containsInRelativeOrder("Europe", "Americas", "Asia", "Middle East and Africa"))
                .body("items.region_id", containsInRelativeOrder(1, 2, 3, 4))
                .extract().jsonPath();

//print all the regions name if we need  to test against with UI ot DB, we will make all region names as a List:
        List<String> regionNames = jsonPath.getList("items.region_name");
        System.out.println("regionNames = " + regionNames);

        //and we get all region names from database

        //compare
    }

    @Test
    public void test3() {

        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and()
                .when().get("/regions").prettyPeek()
                .then()
                .statusCode(200)
                .and()
                .header("Date", is(notNullValue()))
                .body("items[0].region_name", is("Europe"))
                .body("items[0].region_id", is(1))
                .body("items", hasSize(4))
                .body("items.region_name", everyItem(notNullValue()))
                .body("items.region_name", containsInRelativeOrder("Europe", "Americas", "Asia", "Middle East and Africa"))
                .body("items.region_id", containsInRelativeOrder(1, 2, 3, 4))
                .extract().response().jsonPath();

        //choose correct path to store data to List:
        List<Map<String, Object>> list = jsonPath.getList("items");
        System.out.println("list = " + list);
    }

    }
