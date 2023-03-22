package com.cydeo.day03;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class P04_HrWithResponsePath extends HrTestBase {


    @DisplayName("GET request to countries with using Response Path")
    @Test
    public void test1() {

        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\":2}").
                when().get("/countries");

//print limit
        System.out.println("response.path(\"limit\") = " + response.path("limit"));
//print hasMore
        System.out.println("response.path(\"hasMore\") = " + response.path("hasMore"));

//print second county
        System.out.println("response.path(\"items[1].country_id\") = " + response.path("items[1].country_id"));

//print 4th country name:
        System.out.println("response.path(\"items[3].country_name\") = " + response.path("items[3].country_name"));

//print 4th element href
        System.out.println("response.path(\"items[3].links[0].href\") = " + response.path("items[3].links[0].href"));

//print all countries names:
        List<String> allCountries = response.path("items.country_name") ;
        for(String  eachCountryName : allCountries){
            System.out.println("eachCountryName = " + eachCountryName);
        }

 // verify all region_id is 2:
        List<Integer> allRegionId = response.path("items.region_id");
        System.out.println("allRegionId = " + allRegionId);
        for(Integer eachId : allRegionId){
            assertEquals(2, eachId);
        }

        //use Stream to check:
        assertTrue(allRegionId.stream().allMatch(each ->each==2));//return boolean: true if all each is 2

    }
 /*
 send a get request to/employees endpoint to see only job_id = IT_PROG

 query Param:
 q = {"job_id":"IT_PROG"}
 then assert all job_id are  IT_PROG
  */
    @DisplayName("GET request to employees with using query params")
    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON)
                .and()
                .queryParam("q", "{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");

        //then assert all job_id are  IT_PROG
        List<String> listJobId = response.path("items.job_id");
        for(String each : listJobId){
            assertEquals("IT_PROG", each);
        }

    }




}
