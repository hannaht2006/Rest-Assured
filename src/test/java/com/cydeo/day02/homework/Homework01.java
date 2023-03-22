package com.cydeo.day02.homework;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class Homework01 {

    @BeforeAll
    public static void init(){
        RestAssured.baseURI="http://34.227.149.74:1000";
    }
 /*
 Q1:
- Given accept type is Json
- When users sends request to /countries/US
- Then status code is 200
- And Content - Type is application/json
- And response contains United States of America
  */

    @Test
    public void task01(){
        Response response = given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/ords/hr/countries/US");


        //print
        response.prettyPrint();

        //Then status code is 200

        int actualStatusCode = response.statusCode();
        Assertions.assertEquals(200,actualStatusCode );

        //And Content - Type is application/json
        String actualContentType = response.contentType();
        Assertions.assertEquals("application/json", actualContentType);

        //And response contains United States of America
        Assertions.assertTrue(response.body().asString().contains("United States of America"));
    }

    /*
    Q2:
- Given accept type is Json
- When users sends request to /employees/1
- Then status code is 404
     */

    @Test
    public void task2(){

        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/ords/hr/employees/1");

    //Then status code is 404
    int actualStatusCode = response.statusCode();

    Assertions.assertEquals(404, actualStatusCode);
    }
   /*


Q3:
- Given accept type is Json
- When users sends request to /regions/1
- Then status code is 200
- And Content - Type is application/json
- And response contains Europe
- And header should contains Date
- And Transfer-Encoding should be chunked

 */
@Test
    public void task3(){

    Response response = given().accept(ContentType.JSON)
            .when().get("/ords/hr/regions/1");

    //Then status code is 200
    int actualStatusCode = response.statusCode();
    Assertions.assertEquals(200, actualStatusCode);

    //And Content - Type is application/json
    String actualContentType = response.contentType();
    Assertions.assertEquals("application/json", actualContentType);

    //And response contains Europe
    Assertions.assertTrue(response.body().asString().contains("Europe"));

    //And header should contains Date
//    String actualHeaderContainsDate = response.header("Date");
    boolean hasHeaderWithNameDate = response.headers().hasHeaderWithName("Date");
    Assertions.assertTrue(hasHeaderWithNameDate);

    //And Transfer-Encoding should be chunked
    Assertions.assertEquals("chunked", response.header("Transfer-Encoding"));
}

}
