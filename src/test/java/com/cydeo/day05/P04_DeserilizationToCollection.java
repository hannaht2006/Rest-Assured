package com.cydeo.day05;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P04_DeserilizationToCollection extends SpartanTestBase {
    /*
     Given accept type is application/json
     And Path param id = 10
     When i send GET request to /api/spartans
     Then status code is 200
     And content type is json
     And spartan data matching:
     id > 10
     name>Lorenza
     gender >Female
     phone >3312820936
     */

    @Test
    public void test1() {
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 10)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();

   /*
   approach one --> with response

   to use .as(Map.class) we need to add jackson dependency to pom
   we will use only jackson from now
    */
        Map<String, Object> spatanMap = response.as(Map.class);
        System.out.println("spatanMap = " + spatanMap);

        //   Object id = spatanMap.get("id");
        int id = (int) spatanMap.get("id");
        String name = (String) spatanMap.get("name");
        String gender = (String) spatanMap.get("gender");

        //example: we will verify id from API with Database:
        int idFromDb = 10;
        Assertions.assertEquals(id, idFromDb);

        //approach second --> with JASONPATH

        JsonPath jsonPath = response.jsonPath();
        Map<String, Object> jsonPathMap = jsonPath.getMap("");
        System.out.println("jsonPathMap = " + jsonPathMap);

        int idJson = (int) jsonPathMap.get("id");
        String nameJson = (String) jsonPathMap.get("name");
        String genderJson = (String) jsonPathMap.get("gender");

        System.out.println("idJson = " + idJson);
        System.out.println("nameJson = " + nameJson);
        System.out.println("genderJson = " + genderJson);

    }
@DisplayName("Get all spartans with Java Collection")
    @Test
    public void test2() {

        Response response = given().accept(ContentType.JSON)

                .when().get("/api/spartans")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();

    /*
    what is deserilization/serilization?
     */

    //approach one --> with response
    List<Map<String, Object>> spartanList = response.as(List.class);

    System.out.println("spartanList = " + spartanList);

    for(Map<String, Object>  eachMap : spartanList){
        System.out.println("eachMap = " + eachMap);
    }
    //How to find first spartan info
    System.out.println("spartanList.get(0) = " + spartanList.get(0));

    //How to find first spartan name
    System.out.println("spartanList.get(0).get(\"name\") = " + spartanList.get(0).get("name"));

    //get first spartan id
    System.out.println("spartanList.get(0).get(\"id\") = " + spartanList.get(0).get("id"));

    //how to store first spartan info as a map with response.as() method
    //if you want to convert only specific part of response with response.as() --> it does not have any parameters to get as path of json object. --> we can use here response.path() method to convert this as an object.
    //since we know the type of it we can use and store as Map
    Map<String, Object> firstSpartan  = response.path("[0]");
    System.out.println("firstSpartan = " + firstSpartan);


 //approach second (mostly use method)--> with JASONPATH
     JsonPath jsonPath = response.jsonPath();

     List<Map<String, Object>> listAllSpartanJsonPath = jsonPath.getList("");
    System.out.println("listAllSpartanJsonPath = " + listAllSpartanJsonPath);
    for(Map<String, Object> eachMap : listAllSpartanJsonPath){
        System.out.println("eachMap = " + eachMap);
    }

    //How to find first spartan info
    System.out.println("listAllSpartanJsonPath.get(0) = " + listAllSpartanJsonPath.get(0));

    //How to find first spartan name
    System.out.println("listAllSpartanJsonPath.get(0).get(\"name\") = " + listAllSpartanJsonPath.get(0).get("name"));

    //get first spartan id
    System.out.println("listAllSpartanJsonPath.get(0).get(\"id\") = " + listAllSpartanJsonPath.get(0).get("id"));
}
}