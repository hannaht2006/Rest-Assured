package com.cydeo.day04;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class P01_HrWithJsonPath extends HrTestBase {

    @DisplayName("Get all employees Get all /employees?limit=200 --> JSONPATH")
    @Test
    public void test1(){
/*
given accept type is application/json
and query param limit = 200
when the user send request to /employees
the user will see.....
 */
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("limit", 200)
                .when().get("/employees");

        response.prettyPrint();

//statuscode is 200
        assertEquals(200, response.statusCode());


//get all emails from response
        JsonPath js = response.jsonPath();
        List<String> allEmails = js.getList("items.email");
        System.out.println("allEmail = " + allEmails);

        System.out.println("allEmails.size() = " + allEmails.size());

//get all emails who work in IT_PROG
        System.out.println("=============");
       List<String> allEmailIT = js.getList("items.findAll {it.job_id=='IT_PROG'}.email");

        System.out.println("allEmailIT = " + allEmailIT);
        System.out.println("allEmailIT.size() = " + allEmailIT.size());

//get all employees first name whose salary is more than 10000
        System.out.println("=================");

       List<String> allFirstName = js.getList("items.findAll{it.salary>=10000}.first_name");

        System.out.println("allFirstName = " + allFirstName);
        System.out.println("allFirstName.size() = " + allFirstName.size());

  //get all information from response who has max salary
        System.out.println("=================");

String informationOfMaxSalary = js.getString("items.max{it.salary}");

        System.out.println("informationOfMaxSalary = " + informationOfMaxSalary);

   //get first name from response who has max salary
        System.out.println("=================");
     String firstNameOfMaxSalary = js.getString("items.max{it.salary}.first_name");
        System.out.println("firstNameOfMaxSalary = " + firstNameOfMaxSalary);


 //get first name from response who has min salary
        System.out.println("=================");
        String firstNameOfMinSalary = js.getString("items.min{it.salary}.first_name");
        System.out.println("firstNameOfMinSalary = " + firstNameOfMinSalary);
    }

 /*

    TASK
    Given
             accept type is application/json
     When
             user sends get request to /locations
     Then
             response status code must be 200
             content type equals to application/json
             get the second city with JsonPath
             get the last city with JsonPath
             get all country ids
             get all city where their country id is UK

      */
 @DisplayName("Get all employees Get all /location --> JSONPATH")
 @Test
 public void test2() {
     Response response = given().accept(ContentType.JSON)
             .when().get("/locations");

//response status code must be 200
assertEquals(200,response.statusCode());

// content type equals to application/json
assertEquals("application/json", response.contentType());

// get the second city with JsonPath
JsonPath jsonPath = response.jsonPath();

String secondCity = jsonPath.getString("items[1].city");
     System.out.println("secondCity = " + secondCity);

// get the last city with JsonPath
     String lastCity = jsonPath.getString("items[-1].city");
     System.out.println("lastCity = " + lastCity);

// get all country ids
 List <Integer> allIds = jsonPath.getList("items.country_id");
     System.out.println("allIds = " + allIds);

// get all city where their country id is UK
List <String> allCity = jsonPath.getList("items.findAll{it.country_id=='UK'}.city");
     System.out.println("allCity = " + allCity);
 }
}
