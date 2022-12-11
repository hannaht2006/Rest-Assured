package com.cydeo.day05;

import com.cydeo.utilities.ZipCodeTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class Homework02 extends ZipCodeTestBase {

     /*
TASK 2
Given Accept application/json
And path zipcode is 5000
When I send a GET request to /us endpoint
Then status code must be 404
And content type must be application/json
 */

    @DisplayName("Get zipcode 5000")
    @Test
    public void testZipCode() {
        given().accept(ContentType.JSON)
                .pathParam("zipcode", 5000)
                .when().get("/us/{zipcode}")
                .prettyPeek()
                .then()
                .statusCode(404)
                .contentType("application/json");

    }

    @DisplayName("ZipCode path param VA Fairfax")
    @Test
    public void testZipCodeVaFairfax() {
    /*
    TASK 3
Given Accept application/json
And path state is va
And path city is fairfax
When I send a GET request to /us endpoint
Then status code must be 200
And content type must be application/json
And payload should contains following information
    country abbreviation is US
    country  United States
    place name  Fairfax
    each places must contains fairfax as a value
    each post code must start with 22
     */
given().accept(ContentType.JSON)
        .pathParam("state", "VA")
        .pathParam("city", "Fairfax")
      .when().get("/us/{state}/{city}")
        .prettyPeek()
      .then()
        .statusCode(200)
        .and()
        .contentType("application/json")
        .and()
        .body("'country abbreviation'", is("US"))
        .and()
        .body("country", is("United States"))
        .and()
        .body("places[0].'place name'", is("Fairfax"))
        .and()
        .body("places.'place name'", everyItem(containsString("Fairfax")))
        .and()
        .body("places.'post code'", everyItem(startsWith("22")));
    }
}