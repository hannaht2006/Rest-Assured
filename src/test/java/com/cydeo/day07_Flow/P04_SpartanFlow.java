package com.cydeo.day07_Flow;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class P04_SpartanFlow extends SpartanTestBase {
     /*
    Create a Spartan Flow to run below testCases dynamicly
   - POST     /api/spartans
            Create a spartan Map
                name = "API Flow POST"
                gender="Male"
                phone=1231231231l
            - verify status code 201
            - message is "A Spartan is Born!"
            - take spartanID from response and save as a global variable
    - GET  Spartan with spartanID     /api/spartans/{id}
             - verify status code 200
             - verfiy name is API Flow POST
    - PUT  Spartan with spartanID    /api/spartans/{id}
             Create a spartan Map
                name = "API PUT Flow"
                gender="Female"
                phone=3213213213l
             - verify status code 204
    - GET  Spartan with spartanID     /api/spartans/{id}
             - verify status code 200
             - verify name is API PUT Flow
    - DELETE  Spartan with spartanID   /api/spartans/{id}
             - verify status code 204
     - GET  Spartan with spartanID   /api/spartans/{id}
             - verify status code 404
    Challenges
       Create @Test annotated method for each Request
       Put them in order to execute as expected
                    - Use your googling skills
                    - How to run Junit5 Tests in order  ?
     */

   public static int id;


@Order(1)
@DisplayName("POST - GET")
    @Test
    public void test1(){

    //Create SpartanPOST Map
    Map<String, Object> spartanPOST = new HashMap<>();
    spartanPOST.put("name", "API Flow POST");
    spartanPOST.put("gender", "Male");
    spartanPOST.put("phone", 1231231231l);

    System.out.println("spartanPOST = " + spartanPOST);

    JsonPath jsonPath = given().accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(spartanPOST)
            .when().post("/api/spartans")
            .prettyPeek()
            .then()
            .statusCode(201)
            .extract().jsonPath();

    String expectedMessage = "A Spartan is Born!";
        id = jsonPath.getInt("data.id");
    System.out.println("id = " + id);

    assertEquals(expectedMessage,jsonPath.getString("success") );

    //GET
    JsonPath jsonPath1 = given().accept(ContentType.JSON)
            .pathParam("id", id)
            .when().get("/api/spartans/{id}")
            .then()
            .statusCode(200)
            .extract().jsonPath();

    assertEquals(spartanPOST.get("name"), jsonPath1.getString("name"));
}
@Order(2)
    @DisplayName("PUT - GET")
    @Test
    public void test2(){


    Map<String, Object> spartanPUT = new HashMap<>();
    spartanPUT.put("name", "API PUT Flow");
    spartanPUT.put("gender", "Female");
    spartanPUT.put("phone", 3213213213l);

        System.out.println("spartanPUT = " + spartanPUT);

//int id=242;

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(spartanPUT)
           .when().put("/api/spartans/{id}")
                .prettyPeek()
           .then()
                .statusCode(204);

        //GET:
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .pathParam("id", id)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .extract().jsonPath();
// Verify name
assertEquals(spartanPUT.get("name"), jsonPath.getString("name"));

    }
@Order(3)

   @DisplayName("DELETE - GET")
    @Test
    public void test3(){
//id =242;

//DELETE
given().contentType(ContentType.JSON)
        .pathParam("id", id)
        .when().delete("/api/spartans/{id}")
        .then()
        .statusCode(204);

//GET
            given().accept(ContentType.JSON)
                    .pathParam("id", id)
                    .when().get("/api/spartans/{id}")
                    .then()
                    .statusCode(404);

    }

}
