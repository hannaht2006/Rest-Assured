package com.cydeo.day07;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class P02_SpartanPOST extends SpartanTestBase {


    /**
     Given accept type and Content type is JSON
     And content_type json
     and request json body is:
     {
     "gender":"Male",
     "name":"John Doe",
     "phone":8877445596
     }
     When user sends POST request to '/api/spartans'
     Then status code 201
     And content type should be application/json
     And json payload/response/body should contain:
     verify the success value is A Spartan is Born!
     "name": "John Doe",
     "gender": "Male",
     "phone": 1231231231
     */

    @DisplayName("POST spartan with String body")
    @Test
    public void test1(){
       String requestBody = "{\n" +
               "     \"gender\":\"Male\",\n" +
               "     \"name\":\"John Doe\",\n" +
               "     \"phone\":8877445596\n" +
               "     }";

       String expectedMessage = "A Spartan is Born!";

        JsonPath jsonPath = given().accept(ContentType.JSON)//API send me response in JSON formant
                .contentType(ContentType.JSON)//API , I'm sending body in ISON pormant
                .body(requestBody)//this is the data that we wanna POST in API
                .when().post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        assertEquals(expectedMessage, jsonPath.getString("success"));
        assertEquals("John Doe", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals(8877445596l, jsonPath.getLong("data.phone"));

        //if we want id:
        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);

        //if you want to do assertion with GET Request we can use it as PAth param to specify same Spartans
    }

    @DisplayName("POST spartan with Map")
    @Test
    public void test2(){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Math Damon");
        requestBody.put("gender","Male" );
        requestBody.put("phone",8877445596L );
 //we can create SpartanUtil to create dynamic Spartan as Map to use in request
 //  Map<String, Object> spartanMap = SpartanUtil.getSpartanAsMap();

//can we add more info --> no, we cannot

        String expectedMessage = "A Spartan is Born!";

//body(requestBody) --> it is doing serilization behind the scence to send data in JSON format
 //to do serilization we need to one the ObjectMapper (Jackson / Gson)
        JsonPath jsonPath = given().accept(ContentType.JSON)//API send me response in JSON formant
                .contentType(ContentType.JSON)//API , I'm sending body in ISON pormant
                .body(requestBody)//this is the data that we wanna POST in API
                .when().post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        assertEquals(expectedMessage, jsonPath.getString("success"));

        assertEquals("Math Damon", jsonPath.getString("data.name"));
        //different way:
        assertEquals(requestBody.get("name"), jsonPath.getString("data.name"));

        assertEquals("Male", jsonPath.getString("data.gender"));
       assertEquals(requestBody.get("gender"), jsonPath.getString("data.gender"));

        assertEquals(8877445596l, jsonPath.getLong("data.phone"));

        //if we want id:
        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);


    }

    @DisplayName("POST spartan with Spartan POJO body")
    @Test
    public void test3(){
        Spartan requestBody = new Spartan();
        requestBody.setName("John Wick");
        requestBody.setGender("Male");
        requestBody.setPhone(1234567890L);

        System.out.println("requestBody = " + requestBody);

        String expectedMessage = "A Spartan is Born!";

//body(requestBody) --> it is doing serilization behind the scence to send data in JSON format
        //to do serilization we need to one the ObjectMapper (Jackson / Gson)
        JsonPath jsonPath = given().accept(ContentType.JSON)//API send me response in JSON formant
                .contentType(ContentType.JSON)//API , I'm sending body in ISON pormant
                .body(requestBody)//this is the data that we wanna POST in API
                .when().post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        assertEquals(expectedMessage, jsonPath.getString("success"));

        assertEquals(requestBody.getName(), jsonPath.getString("data.name"));


        assertEquals(requestBody.getGender(), jsonPath.getString("data.gender"));


        assertEquals(requestBody.getPhone(), jsonPath.getLong("data.phone"));

        //if we want id:
        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);


    }

    @DisplayName("POST spartan with Spartan POJO body and GET same Spartan")
    @Test
    public void test4(){
        Spartan requestBody = new Spartan();
        requestBody.setName("John Wick");
        requestBody.setGender("Male");
        requestBody.setPhone(1234567890L);

        System.out.println("requestBody = " + requestBody);

        String expectedMessage = "A Spartan is Born!";

//POST request:
        JsonPath jsonPath = given().accept(ContentType.JSON)//API send me response in JSON formant
                .log().body()
                .contentType(ContentType.JSON)//API , I'm sending body in ISON pormant
                .body(requestBody)//this is the data that we wanna POST in API
                .when().post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        //if we want id:
        int idFromPOST = jsonPath.getInt("data.id");
        System.out.println("id = " + idFromPOST);

   // GET SAME SPARTAN WITH SAME ID THAT WE GET FROM POST RESPONSE:
        Spartan spartanGET = given().accept(ContentType.JSON)
                .pathParam("id", idFromPOST)
                .when().get("/api/spartans/{id}").then()
                .statusCode(200)
                .extract().jsonPath().getObject("", Spartan.class);
//we just create SpartanGET in 1 shot by adding getObject("", Spartan.class) to the request line as above

        System.out.println("spartanGet = " + spartanGET);

        assertEquals(expectedMessage, jsonPath.getString("success"));

  //verifying:
        assertEquals(requestBody.getName(), spartanGET.getName());

        assertEquals(requestBody.getGender(), spartanGET.getGender());

        assertEquals(requestBody.getPhone(), spartanGET.getPhone());




    }
}
