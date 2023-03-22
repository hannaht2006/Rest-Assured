package com.cydeo.day12_DDT;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.everyItem;



public class P04_CsvSource {

    @ParameterizedTest
    @CsvSource({"1, 3, 4",
                "2, 3, 5",
                "3, 4, 7",
                "5, 6, 11"})

    public void test1(int num1, int num2, int sum){
        System.out.println(num1 +"+" + num2 +" = " + sum);
        Assertions.assertEquals(sum,(num1+num2));
    }

    // Write a parameterized test for this request
    // GET https://api.zippopotam.us/us/{state}/{city}
    /*
        "NY, New York",
        "CO, Denver",
        "VA, Fairfax",
        "MA, Boston",
        "NY, New York",
        "MD, Annapolis"
     */
    //verify place name contains your city name
    //print number of places for each request

    @ParameterizedTest
    @CsvSource({"NY, New York",
            "CO, Denver",
            "VA, Fairfax",
            "MA, Boston",
            "MD, Annapolis"
    })
    public void test2(String state, String city){

        int placeNumbers = given().accept(ContentType.JSON)
                .pathParam("state", state)
                .pathParam("city", city)
                .when().get("https://api.zippopotam.us/us/{state}/{city}")
                .then()
                .statusCode(200)
                .body("places.'place name'", everyItem(containsString(city))).extract().jsonPath().getList("places").size();

        //Print number of places for each request:
        System.out.println( city +" has " + placeNumbers +" zipcode");

        //different way with stream and lambda
      //  assertTrue(jsonPath.getList("places.'place name'").stream().allMatch(p -> p.toString().contains(city)));

    }
}
