package com.cydeo.day06;

import com.cydeo.utilities.FomulaTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class Homework04 extends FomulaTestBase {
    /*
    HOMEWORK-FormulaAPI INFO --> http://ergast.com/mrd/methods/
drivers/
- It's for historical formula one race information
- In this particular api , it decided to give you ml by default for response type and In this particular api , it decided to give you json if you add .json at the end of url

FOR EXAMPLE
- http://ergast.com/api/f1/drivers.json ---> return JSON
BASE URL —> http://ergast.com/api/f1/

    TASK 1 :
NOTE --> Solve same task with 4 different way
- Use JSONPATH
int total = jsonpath.getInt(“pathOfField”)

- Use HAMCREST MATCHERS
then().body(..........)
Print givenName of Driver by using extract method after HamCrest
- Convert Driver information to Java Structure
Map<String,Object> driverMap=jsonpath.getMap(“pathOfDriver”)
- Convert Driver information POJO Class
Driver driver=getObject(“pathOfDriver”,Driver.class)
- Given accept type is json
- And path param driverId is alonso
- When user send request /drivers/{driverId}.json
- Then verify status code is 200
- And content type is application/json; charset=utf-8 - And total is 1
- And givenName is Fernando
- And familyName is Alonso
- And nationality is Spanish
     */

    @DisplayName("Task1 - JsonPath")
    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
              .when().get("/drivers.json");

        assertEquals(200, response.statusCode());

        assertEquals("application/json; charset=utf-8", response.contentType());

         JsonPath js = response.jsonPath();
       int total = js.getInt("MRData.total");
        System.out.println("total = " + total);

       String url = js.getString("MRData.url");
        System.out.println("url = " + url);



    }

    @DisplayName("Task1 - Hamcrest")
    @Test
    public void test2(){
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .pathParam("driverId", "alonso")
                .when().get("/drivers/{driverId}.json")
                .prettyPeek()
                .then()
                .statusCode(200)
                .and()
                .contentType("application/json; charset=utf-8")
                .body("MRData.total", is("1"))
                .body("MRData.DriverTable.Drivers[0].givenName", is("Fernando"))
                .body("MRData.DriverTable.Drivers[0].dateOfBirth", is("1981-07-29"))
                .extract().jsonPath();

    }
}
