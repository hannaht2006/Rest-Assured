package com.cydeo.day03;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class P03_SpartanWithResponseBase extends SpartanTestBase {
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
    @Test
    public void test1() {
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 10)
                .when().get("api/spartans/{id}");

        response.prettyPrint();

//        Then status code is 200
    assertEquals(200, response.statusCode());
//      And content-type is "application/json"
     assertEquals("application/json", response.contentType());

//      And response payload values match the following:
//           id is 10,
//           name is "Lorenza",
//           gender is "Female",
//           phone is 3312820936
int id = response.path("id");

        System.out.println("id: " + id);
String name = response.path("name");
String gender = response.path("gender");
long phoneNumber = response.path("phone");
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phoneNumber = " + phoneNumber);

//if the path is incorrect, it will return NULL

        String address = response.path("address");
        System.out.println("address = " + address);//--> NULL

 //assertion:
 assertEquals(10, id);
 assertEquals("Lorenza", name);
 assertEquals("Female", gender);
 assertEquals(3312820936l, phoneNumber);
    }

    @DisplayName("Get all Spartans")
@Test
    public void test2(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");

//        response.prettyPrint();

  //get me first spartan id
  int firstId = response.path("[0].id");
  int idFirst = response.path("id[0]");//same as above
        System.out.println("firstId: " + firstId);
  //get me first spartan name
  String firstName = response.path("[0].name");

  // get me first spartan name
        System.out.println("response.path(\"name[0]\") = " + response.path("name[0]"));

  //get me the last spartan name
        //"name[-1]" --> -1 refers last element of the name list
        //with the help of GPATH we can get data
   String nameAtLast = response.path("name[-1]");
        System.out.println("nameAtLast = " + nameAtLast);

        //get me the second from last spartan name
        String nameAtSecondLast = response.path("name[-2]");
        System.out.println("nameAtSecondLast = " + nameAtSecondLast);

        //get all spartan name:
        List<String> allName = response.path("name");

  //how to print all:
  for(String eachName : allName)   {
      System.out.println("eachName = " + eachName);
  }


    }

}
