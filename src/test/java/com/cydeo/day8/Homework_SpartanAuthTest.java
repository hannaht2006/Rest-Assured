package com.cydeo.day8;

import com.cydeo.utilities.SpartanAuthTestBase;
import com.cydeo.utilities.SpartanTestBase;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static java.util.Map.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Homework_SpartanAuthTest extends SpartanAuthTestBase {

    /**
     *  HOMEWORKS
     *
     *      Role Based Control Test --> RBAC
     *
     *             ADMIN  -->  GET  POST PUT PATCH  DELETE   --> Spartan Flow
     *             EDITOR -->  GET  POST PUT PATCH   403
     *             USER   -->  GET  403  403  403    403
     *             GUEST  -->  401  401  401  401    401
     *
     *
     *   -- Create RBAC Test for all different roles from Spartan Application including with Negative Test cases
     *   -- Create SpartanUtil Class
     *             public static Map<String,Object>  SpartanUtil.getRandomSpartan(){
     *                  Map<String,Object> spartanMap=new HashMap();
     *                  spartanMap.put("name",faker.funnyName());
     *
     *                  use Faker class to create each time differetn spartan information
     *
     *                  return spartanMap;
     *              }
     *
     *               public static void  GETSpartans(String role,String password,int statusCode,int id){
     *
     *                 given().pathParam("id",id)
     *                 .auth().basic(role,password).
     *                 when().delete("/api/spartans/{id}").then().statusCode(statusCode);
     *
     *               }
     *
     *     Q --> can we create class in utilities class with loop for each user, admin, guest with passport make more dynamic
     *            - YES we can but what if first user test is failing.
     *
     *            - We should do it --> Data Driven Test
     *
     *            - JUnit5 DDT to implement
     *
     */

    static int id;
    static int id2;
    static int id3;
    static int id4;
    Map<String, Object> spartanPost = Map.of(
            "name", "John Dan",
          "gender", "Male",
          "phone", 1231231230l);

    Map<String, Object> spartanPut = Map.of(
            "name", "John Put",
            "gender", "Female",
            "phone", 1234567890L);

    @Order(1)
    @DisplayName("Post - Admin")
    @Test
    public void test1(){
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .contentType("application/json")
                .auth().basic("admin", "admin")
                .body(spartanPost)
                .when().post("/api/spartans")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().jsonPath();

        id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);
    }

    @Order(2)
    @DisplayName("GET - Admin")
    @Test
    public void test2(){
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .pathParam("id", id)
                .auth().basic("admin", "admin")
                .when().get("/api/spartans/{id}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath();

        assertEquals(spartanPost.get("name"), jsonPath.getString("name"));

        System.out.println("id after Get= " + id);
    }

    @Order(3)
    @DisplayName("PUT - Admin")
    @Test
    public void test3(){
        JsonPath jsonPath = given()
                .contentType("application/json")
                .pathParam("id", id)
                .auth().basic("admin", "admin")
                .body(spartanPut)
                .when().put("/api/spartans/{id}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().jsonPath();


        System.out.println("id after Put= " + id);
    }
    @Order(4)
    @DisplayName("GET - Admin")
    @Test
    public void test4(){
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .pathParam("id", id)
                .auth().basic("admin", "admin")
                .when().get("/api/spartans/{id}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath();

        assertEquals(spartanPut.get("name"), jsonPath.getString("name"));

        System.out.println("id after Get= " + id);
    }

    @Order(5)
    @DisplayName("DELETE - Admin")
    @Test
    public void test5(){

        given()
                .pathParam("id", id)
                .auth().basic("admin", "admin")
                .when().delete("/api/spartans/{id}")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

    }

    @Order(6)
    @DisplayName("GET - Admin")
    @Test
    public void test6(){
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .pathParam("id", id)
                .auth().basic("admin", "admin")
                .when().get("/api/spartans/{id}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract().jsonPath();

}

    @Order(7)
    @DisplayName("Post - editor")
    @Test
    public void test7(){
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .contentType("application/json")
                .auth().basic("editor", "editor")
                .body(spartanPost)
                .when().post("/api/spartans")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().jsonPath();

        id2 = jsonPath.getInt("data.id");
        System.out.println("id = " + id2);
    }

    @Order(8)
    @DisplayName("GET - Editor")
    @Test
    public void test8(){
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .pathParam("id", id2)
                .auth().basic("editor", "editor")
                .when().get("/api/spartans/{id}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath();

        assertEquals(spartanPost.get("name"), jsonPath.getString("name"));

        System.out.println("id after Get= " + id2);
    }

    @Order(9)
    @DisplayName("PUT - Editor")
    @Test
    public void test9(){
        JsonPath jsonPath = given()
                .contentType("application/json")
                .pathParam("id", id2)
                .auth().basic("editor", "editor")
                .body(spartanPut)
                .when().put("/api/spartans/{id}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().jsonPath();


        System.out.println("id after Put= " + id2);
    }
    @Order(10)
    @DisplayName("GET - Editor")
    @Test
    public void test10(){
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .pathParam("id", id2)
                .auth().basic("editor", "editor")
                .when().get("/api/spartans/{id}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath();

        assertEquals(spartanPut.get("name"), jsonPath.getString("name"));

        System.out.println("id after Get= " + id2);
    }

    @Order(11)
    @DisplayName("DELETE - Editor")
    @Test
    public void test11(){

        given()
                .pathParam("id", id2)
                .auth().basic("editor", "editor")
                .when().delete("/api/spartans/{id}")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);

    }

    @Order(12)
    @DisplayName("GET - Editor")
    @Test
    public void test12(){
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .pathParam("id", id2)
                .auth().basic("editor", "editor")
                .when().get("/api/spartans/{id}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath();

    }
}