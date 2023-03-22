package com.cydeo.day03;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class P05_SpartanWithJsonPath extends SpartanTestBase {

    /*
     Given accept type is json
     And path param id is 10
     When user sends a get request to "api/spartans/{id}"
     Then status code is 200
     And content-type is "application/json"
     And response payload values match the following:
          id is 10,
          name is "Lorenza",
          gender is "Female",
          phone is 3312820936
   */
    @DisplayName("Get spartan with Json path")
    @Test
    public void test1() {
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 10)
                .when().get("api/spartans/{id}");

        response.prettyPrint();
//Then status code is 200
        assertEquals(200, response.statusCode());

// We saved response as JSONPATH OBJECT
        JsonPath jsonPath = response.jsonPath();

        //is it possible to get stsatusCode / ContentType / Headers with JsonPath
        //if you wanna do assertions for them, still we need to use response object

        int id = jsonPath.getInt("id");
        String name = jsonPath.getString("name");
        String genderv= jsonPath.getString("gender");
        long phone = jsonPath.getLong("phone");

        System.out.println("jsonPath.getInt(\"id\") = " + jsonPath.getInt("id"));
//assertion:

        assertEquals(10, id);


    }


}