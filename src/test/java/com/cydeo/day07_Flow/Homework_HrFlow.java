package com.cydeo.day07_Flow;

import com.cydeo.pojo.Region;
import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Homework_HrFlow extends HrTestBase {
  /*
  ORDS API DOCUMENT
—> https://documenter.getpostman.com/view/13495735/2s847LMqvX

TASK 1:
—> POST a region then do validations. Please use Map or POJO class, or JsonPath

 Given accept is json
 and content type is json
 When I send post request to "/regions/"
 With json:
 {
 "region_id":100,
 "region_name":"Test Region"
 }
 Then status code is 201
 content type is json
 region_id is 100
 region_name is Test Region

 Given accept is json
 When I send GET request to "/regions/100"
 Then status code is 200
 content type is json
 region_id is 100
region_name is Test Region

TASK 2:
—-> PUT request then DELETE

Given accept type is Json
And content type is json
When i send PUT request to /regions/100
With json body:
 {
 "region_id": 100,
 "region_name": "Wooden Region"
 }
 Then status code is 200
And content type is json
region_id is 100
region_name is Wooden Region
—> DELETE
 Given accept type is Json
 When i send DELETE request to /regions/100
 Then status code is 200

   */

    public static int id;

    @Order(1)
    @DisplayName("POST")
    @Test
    public void test1(){

        Region region = new Region();
        region.setRegionId(250);
        region.setRegionName("Test Region");

        System.out.println("region = " + region);

        JsonPath jsonPath = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(region)
                .when().post("/regions/")
                .prettyPeek()
                .then()
                .statusCode(201)
               .contentType("application/json")
                .extract().jsonPath();
   assertEquals("Test Region", jsonPath.getString("region_name"));
    assertEquals(region.getRegionId(),jsonPath.getInt("region_id"));

    id = jsonPath.getInt("region_id");
        System.out.println("id = " + id);

        //GET
        JsonPath jsonPath1 = given().accept(ContentType.JSON)
                .pathParam("region_id", id)
                .when().get("/regions/{region_id}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().jsonPath();

        assertEquals("Test Region", jsonPath1.getString("region_name"));
        assertEquals(region.getRegionId(),jsonPath1.getInt("region_id"));

    }

    @Order(2)
    @DisplayName("PUT")
    @Test
    public void test2() {
     Region regionPut = new Region();
     regionPut.setRegionName("Wooden Region");
     regionPut.setRegionId(id);

     given().log().body()
             .contentType(ContentType.JSON)
             .pathParam("region_id", id)
             .body(regionPut)
             .when().put("/regions/{region_id}")
             .prettyPeek()
             .then()
             .statusCode(200);

     //GET
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .pathParam("region_id", id)
                .when().get("/regions/{region_id}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().jsonPath();

        assertEquals(regionPut.getRegionId(), jsonPath.getInt("region_id"));
        assertEquals(regionPut.getRegionName(), jsonPath.getString("region_name"));

    }
    @Order(3)
    @DisplayName("DELETE")
    @Test
    public void test3(){

        JsonPath jsonPath = given().log().body()
                .pathParam("region_id", id)
                .when().delete("/regions/{region_id}")
                .then().statusCode(200)
                .extract().jsonPath();

        //GET
        given().accept(ContentType.JSON)
                        .pathParam("region_id", id)
                  .when().get("/regions/{region_id}")
                  .then()
                        .statusCode(404);


    }

}
