package com.cydeo.day13;

import com.cydeo.utilities.BookITUtils;
import com.cydeo.utilities.BookItTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class P03_BookitSPecTest extends BookItTestBase {
    /**
     *
     * Send a request /api/users/me endpoint as a teacher
     * verify statuscode is 200
     * contentType will be ContentType.JSON
     */
 @Test
 public void test1(){
     given().log().all()
     .header("Authorization", BookITUtils.getTokenByRole("teacher"))
             .accept(ContentType.JSON)
             .when().get("/api/users/me")
             .then()
             .statusCode(200)
             .contentType(ContentType.JSON);

 }

    @Test
    public void test2(){
        given().spec(BookITUtils.getReqSpec("teacher"))
                .when().get("/api/users/me")
                .then()
                .spec(BookITUtils.getResSpec(200))
                .body("firstName", is("Barbabas"));

    }

    /**
     * HW:
     *
     * create a Parameterized test by using csv file as userinfo.csv
     * role, firstname
     * student-member, Raymond
     * student-leader, Lissie
     * teacher, Barbabas
     *
     * send GET request /api/users/me  for each data
     * verify status code 200
     * contentType json
     * firstName is firstName from csv file
     *
     */

    @ParameterizedTest
    @CsvFileSource(resources = "/userinfo.csv", numLinesToSkip = 1)
    public void test3(String role, String firstname){
        given().spec(BookITUtils.getReqSpec(role))
                .when().get("/api/users/me")
                .then().spec(BookITUtils.getResSpec(200))
                .body("firstName", is(firstname));
    }

}
