package com.cydeo.day12;

import com.cydeo.utilities.BookItTestBase;
import com.cydeo.utilities.BookitQa3TestBase;
import com.cydeo.utilities.ExcelUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class P08_BookitLoginDDTTest extends BookitQa3TestBase {


//Create a method to read bookitqa3 excel file information (QA3 Sheet)
    //use those information as an email and password to send a get request to /sign endpoint
    //verify you got 200 for each request
    //print accessToken for each request

 @ParameterizedTest
 @MethodSource("getCredentials")
 public void test1(Map<String, String> userInfo){

       String accessToken = given().accept(ContentType.JSON)
                 .queryParams(userInfo)
                 .when().get("/sign")
                 .prettyPeek()
                 .then()
                 .statusCode(200)
                 .extract().jsonPath().getString("accessToken");

         System.out.println(accessToken);
     }
    public static List<Map<String, String>> getCredentials() {
        ExcelUtil bookitQa3 = new ExcelUtil("src/test/resources/BookItQa3.xlsx","QA3");

        return bookitQa3.getDataList();
    }

 }


