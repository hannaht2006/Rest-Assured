package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class P06_HrWithJsonPath extends HrTestBase {

 @DisplayName("Get all countries")
    @Test
    public void test1(){
     Response response = get("/countries");
     response.prettyPrint();

     assertEquals(200, response.statusCode());

 //let create JsonPath:
     JsonPath jsonPath = response.jsonPath();

     //get me the 3rd country info:
     System.out.println("jsonPath.getString(\"items[2]\") = " + jsonPath.getString("items[2]"));

     //can we store them as MAP
     //yes we can. We are gonna do later

     //get me the 3rd country name
     String countryThird = jsonPath.getString("items[2].country_name");
     System.out.println("countryThird = " + countryThird);

     //get me 3rd and 4th country name:
     System.out.println("jsonPath.getString(\"items[2,3].country_name\") = " + jsonPath.getString("items[2,3].country_name"));

     //get me all country name where region_id is 2
     List<String> list  = jsonPath.getList("items.findAll {it.region_id}.country_name");
     System.out.println("list= " +list);

     /*
     it refers each item from the items array
      */


 }



}
