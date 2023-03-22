package com.cydeo.day12;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

public class P03_ValueSourceTest {

    @ParameterizedTest
    @ValueSource(ints={10, 20, 30, 40, 50})
   // @Test --> if we use @ParameterizedTest, don't use @tTest
    public void test1(int number){
        System.out.println(number);
        Assertions.assertTrue(number<40);

    }

    @ParameterizedTest(name =" {index} name is {0}")
    @ValueSource(strings={"Mike", "Rose", "Kimberly", "TJ", "King"})
    // @Test --> if we use @ParameterizedTest, don't use @tTest
    public void test2(String name){
        System.out.println(name);
        Assertions.assertTrue(name.length()<5);
//{index} --> it will print in console as test name with their index
        //{0} --> will get data from the provided set of data. 0 point to first set of data
    }

    // SEND GET REQUEST TO https://api.zippopotam.us/us/{zipcode}
    // with these zipcodes 22030,22031, 22032, 22033 , 22034, 22035, 22036
    // check status code 200

    @ParameterizedTest(name =" {index} zipCode is {0}")
    @ValueSource(ints ={22030,22031, 22032, 22033 , 22034, 22035, 22036})
    public void test3(int zipCode){

        given().accept(ContentType.JSON)
                .pathParam("zipcode", zipCode)
                .when().get("https://api.zippopotam.us/us/{zipcode}")
                .then()
                .statusCode(200);
    }

    @ParameterizedTest(name =" {index} zipCode is {0}")
    @ValueSource(ints ={22030,22031, 22032, 22033 , 22034, 22035, 22036})
    public void test4(int zipCode){

        given().baseUri("https://api.zippopotam.us")//Same url for all API request
                .pathParam("zipcode", zipCode)
         .when().get("/us/{zipcode}").prettyPeek()
                .then()
                .statusCode(200);

        /*
        IP:8000  --> baseURI
        /api --> basePath
        /spartans --> endpoint
         */
    }


}
