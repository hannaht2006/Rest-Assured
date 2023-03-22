package com.cydeo.day12;

import com.cydeo.utilities.ExcelUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P03_review {

    @ParameterizedTest
    @ValueSource(ints = {2, 5, 7})
    public void test1(int num) {
        System.out.println(num);
        Assertions.assertTrue(num < 6);
    }

    @ParameterizedTest
    @ValueSource(strings = {"hannah", "Catherine", "Kristina", "TJ"})
    public void test2(String name) {
        Assertions.assertTrue(name.length() > 6);
    }

    @ParameterizedTest
    @ValueSource(ints = {22030, 22031, 22032, 22033, 22034, 22035, 22036})
    public void test3(int zipcode) {
        System.out.println(zipcode);
        given().baseUri("https://api.zippopotam.us")
                .pathParam("zipCode", zipcode)
                .when().get("/us/{zipCode}").prettyPeek()
                .then().statusCode(200);
    }

    @ParameterizedTest
    @CsvSource({"1,2,3,6",
            "2,8,10,3",
            "5,6,11,3",
            "4,9,2,3"})
    public void test4(int num1, int num2, int sum, int num4) {
        Assertions.assertEquals(sum, (num1 + num2));
    }

    @ParameterizedTest
    @CsvSource({"NY, New York",
            "VA, Fairfax",
            "CO, Denver",
            "MD, Annapolis"})
    public void test5(String state, String city) {

        int placeNumber = given().baseUri("https://api.zippopotam.us")
                .pathParam("State", state)
                .pathParam("City", city)
                .when().get("/us/{State}/{City}").prettyPeek()
                .then().statusCode(200)
                .body("places.'place name'", everyItem(containsString(city))).extract().jsonPath().getList("places").size();
        System.out.println(placeNumber);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/places.csv", numLinesToSkip = 1)
    public void test5(String state, String city, int zipCount) {
        given().baseUri("https://api.zippopotam.us").
                pathParam("State", state)
                .pathParam("City", city).
                when().get("/us/{State}/{City}").
                then().statusCode(200).
                body("places", hasSize(zipCount));
    }

    @ParameterizedTest
    @MethodSource("getName")
    public void test6(String name) {
        System.out.println("name: " + name);
    }

    public static List<String> getName() {
        List<String> nameList = Arrays.asList("Kimbely", "King", "TJ", "Bond");
        return nameList;
    }

    @Test
    public void test7() {

        ExcelUtil library = new ExcelUtil("src/test/resources/Library.xlsx", "library1-short");
        List<Map<String, String>> userInfo = library.getDataList();
for(Map<String, String> eachUser : userInfo){
    System.out.println(eachUser);
}

    }

    @ParameterizedTest
    @MethodSource("getExcelData")
    public void testUserData(Map<String, String> userInfo){
        System.out.println(userInfo);
    }
    public static List<Map<String, String>> getExcelData(){
        ExcelUtil library = new ExcelUtil("src/test/resources/Library.xlsx", "library1-short");
        return library.getDataList();
    }
}
