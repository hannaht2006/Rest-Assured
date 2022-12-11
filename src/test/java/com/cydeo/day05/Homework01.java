package com.cydeo.day05;

import com.cydeo.utilities.CydeoTrainingTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class Homework01 extends CydeoTrainingTestBase {

    /*
     Given accept type is application/json
    and path param is 2
    When user send request /teacher/{id}
    Then user should see status code 200
    And contetn type is application/json
    And date header is exist
    And transfer-encoding is chunked
    And firstname is Ron
    And lastname is Tona
    And gender is Male
     */

 @DisplayName("get teacher from Cydeo training")
    @Test
    public void test1(){
     given().accept(ContentType.JSON)
             .pathParam("id", 2)
             .and()
        .when().get("/teacher/{id}")
             .prettyPeek()
        .then()
             .statusCode(200)
             .contentType("application/json")
             .header("date", is(notNullValue()))
             .header("transfer-encoding", is("chunked"))
             .body("teachers[0].firstName", is("Ron"))
             .body("teachers[0].lastName", is("Tona"))
             .body("teachers[0].gender", is("Male"));

 }


}
