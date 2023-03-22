package com.cydeo.day06_DeSeri;

import com.cydeo.utilities.HrTestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class P01_HRDeserilization extends HrTestBase {
/**
 * Create a test called getLocation
 * 1. Send request to GET /locations
 * 2. log uri to see
 * 3. Get all Json as a map and print out screen all the things with the help of  map
 * System.out.println("====== GET FIRST LOCATION  ======");
 * System.out.println("====== GET FIRST LOCATION LINKS  ======");
 * System.out.println("====== GET ALL LOCATIONS AS LIST OF MAP======");
 * System.out.println("====== FIRST LOCATION ======");
 * System.out.println("====== FIRST LOCATION ID ======");
 * System.out.println("====== FIRST LOCATION COUNTRY_ID ======");
 * System.out.println("====== GET FIRST LOCATION FIRST LINK  ====== ");
 * System.out.println("====== LAST LOCATION ID ======");
 */
    @DisplayName("Get /locations into Hava collection")
    @Test
    public void test1(){

        JsonPath jsonPath = given().log().uri()
                .when().get("/locations")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract().response().jsonPath();

        System.out.println("====get 1st location=======");

        Map<String, Object> firstMap = jsonPath.getMap("items[0]");
        System.out.println("firstMap = " + firstMap);

        System.out.println("====== GET FIRST LOCATION LINKS  ======");
       Map<String, Object> firstMapLink = jsonPath.getMap("items[0].links[0]");
        System.out.println("firstMapLink = " + firstMapLink);

 //how to get href value from firstMapLink
        System.out.println("firstMapLink.get(\"href\") = " + firstMapLink.get("href"));

        System.out.println("====== GET ALL LOCATIONS AS LIST OF MAP======");
        List<Map<String, Object>> allLocationMap  = jsonPath.getList("items");
        for(Map<String, Object> eachLocation :allLocationMap){
            System.out.println("each = " + eachLocation);
        }

  System.out.println("====== FIRST LOCATION ======");
        System.out.println("allLocationMap.get(0) = " + allLocationMap.get(0));

        System.out.println("====== FIRST LOCATION ID ======");
        System.out.println("allLocationMap.get(0).get(\"id\") = " + allLocationMap.get(0).get("location_id"));

        System.out.println("====== FIRST LOCATION COUNTRY_ID ======");
        System.out.println("allLocationMap.get(0).get(\"country_id\") = " + allLocationMap.get(0).get("country_id"));

        System.out.println("====== GET FIRST LOCATION FIRST LINK  ====== ");

//cannot do as below:
        System.out.println("allLocationMap.get(0).get(\"links\") = " + allLocationMap.get(0).get("links"));//this way is return an array of links

    //the right way to get 1st link: we need to put links to a List of Map again:
       List<Map<String, Object>> links = (List<Map<String, Object>>)allLocationMap.get(0).get("links"); //need casting(List<Map<String, Object>>) to make it work
        System.out.println("links.get(0).get(\"href\") = " + links.get(0).get("href"));

        System.out.println("====== LAST LOCATION ID ======");
        System.out.println("allLocationMap.get(allLocationMap.size()-1).get(\"location_id\") = " + allLocationMap.get(allLocationMap.size() - 1).get("location_id"));
    }
}
