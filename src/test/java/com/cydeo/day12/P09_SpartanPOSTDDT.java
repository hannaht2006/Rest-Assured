package com.cydeo.day12;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P09_SpartanPOSTDDT extends SpartanTestBase {

    /**
     * Create single spartan with dynamic values
     *
     * given content type is json
     * and accept type is json
     * When I post Spartan
     * and status code needs to be 201
     * verify spartan information matching with dynamic that we provide
     *
     * Generate DUMMY DATA
     * https://www.mockaroo.com/
     *
     * name
     * gender
     * phone
     */

    @ParameterizedTest
    @CsvFileSource(resources = "/Spartan_Data.csv", numLinesToSkip = 1)
    public void test1(String name, String gender, String phone){

        String expectedMessage = "A Spartan is Born!";
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("gender", gender);
        requestBody.put("phone", phone);

        JsonPath jsonPath = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/api/spartans")
                .prettyPeek()
                .then()
                .statusCode(201)
                .extract().jsonPath();

        int id = jsonPath.getInt("data.id");
        assertEquals(requestBody.get("name"), jsonPath.getString("data.name"));
        assertEquals(requestBody.get("gender"), jsonPath.getString("data.gender"));
        assertEquals(requestBody.get("phone"), jsonPath.getString("data.phone"));

        given().accept(ContentType.JSON)
                .pathParam("id", id)
                .when().delete("/api/spartans/{id}")
                .prettyPeek()
                .then()
                .statusCode(204);


        //Create a GET method with same ID to see is it updated
        given().accept(ContentType.JSON)
                .pathParam("id", id)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(404);

        //get 404 not found after delete




    }

}
