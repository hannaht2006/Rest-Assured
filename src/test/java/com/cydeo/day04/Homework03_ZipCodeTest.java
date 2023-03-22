package com.cydeo.day04;

import com.cydeo.utilities.ZipCodeTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class Homework03_ZipCodeTest extends ZipCodeTestBase {

@DisplayName("ZipCode path param")
@Test
    public void test1(){
    /**
     Given accept type is json
     and country path param value is "us"
     and postal code path param value is 22102
     When I send get request to http://api.zippopotam.us/%7Bcountry%7D/%7Bpostal-code%7D
     Then status code is 200
     Then "post code" is "22102"
     And  "country" is "United States"
     And "place name" is "Mc Lean"
     And  "state" is "Virginia"
     */

    Response response = given().accept(ContentType.JSON)
            .and()
            .pathParam("country", "US")
            .and()
            .pathParam("zipcode", 22102)
            .when().get("/{country}/{zipcode}");

// Then status code is 200
    assertEquals(200, response.statusCode());

// Then "post code" is "22102"

    JsonPath jsonPath = response.jsonPath();
    assertEquals("22102", jsonPath.getString("'post code'"));

// And  "country" is "United States"
    assertEquals("United States", jsonPath.getString("country"));

// And "place name" is "Mc Lean"
    assertEquals("Mc Lean", jsonPath.getString("places[0].'place name'"));

// And  "state" is "Virginia"
    assertEquals("Virginia", jsonPath.getString("places[0].state"));
}


    @DisplayName("ZipCode path param")
    @Test
    public void test2() {
/*
        TASK 1
        Given Accept application/json
        And path zipcode is 22031
        When I send a GET request to /us endpoint
        Then status code must be 200
        And content type must be application/json
        And Server header is cloudflare
        And Report-To header exists
        And body should contains following information
        post code is 22031
        country  is United States
        country abbreviation is US
        place name is Fairfax
        state is Virginia
        latitude is 38.8604
*/
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("zipcode", 22031)
                .when().get("/us/{zipcode}");

//  Then status code must be 200
   assertEquals(200, response.statusCode());

// And content type must be application/json
   assertEquals("application/json", response.contentType());

// And Server header is cloudflare
 assertEquals("cloudflare", response.header("Server"));

// And Report-To header exists
 assertTrue(response.headers().hasHeaderWithName("Report-To"));

// And body should contains following information
        JsonPath jsonPath = response.jsonPath();
// assertTrue(response.body().asString().contains("'post code'", 22031));
// post code is 22031
        int postCode = jsonPath.getInt("'post code'");
// country  is United States
        String country = jsonPath.getString("country");
// country abbreviation is US
        String countryAbbreviation = jsonPath.getString("'country abbreviation'");
//        place name is Fairfax
        String placeName = jsonPath.getString("places[0].'place name'");
//        state is Virginia
        String state = jsonPath.getString("places[0].state");
//        latitude is 38.8604
        Double latitude = jsonPath.getDouble("places[0].latitude");

        //assertion:
assertEquals(22031, postCode);
assertEquals("United States", country);
assertEquals("US", countryAbbreviation);
assertEquals("Fairfax", placeName);
assertEquals("Virginia", state);
assertEquals(38.8604, latitude);
    }

    @DisplayName("ZipCode path param 3")
    @Test
    public void test3() {
/*
TASK 2
Given Accept application/json
And path zipcode is 5000
When I send a GET request to /us endpoint
Then status code must be 404
And content type must be application/json
 */

        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("zipcode", 5000)
                .when().get("/us/{zipcode}");

        assertEquals(404, response.statusCode());
        assertEquals("application/json", response.contentType());


    }

    @DisplayName("ZipCode path param 4")
    @Test
    public void test4() {
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
                .and()
                .pathParam("state", "va")
                .and()
                .pathParam("city", "fairfax")
                .when().get("/US/{state}/{city}")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .and().body("'country abbreviation'", Matchers.equalTo("US"), "country", Matchers.equalTo("United States"), "'place name'", Matchers.equalTo("Fairfax"),"places", Matchers.containsStringIgnoringCase("Fairfax"), "'post code'", Matchers.startsWith("22"));

    }
}
