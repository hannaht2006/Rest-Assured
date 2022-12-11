package com.cydeo.day06;

import com.cydeo.pojo.Search;
import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class P02_SpartanDeserilizationPOJO extends SpartanTestBase {

    @DisplayName("Get single Spartan for deserilization to POJO")
    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get("/api/spartans/{id}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract().response();

        /*
        {
    "id": 15,
    "name": "Meta",
    "gender": "Female",
    "phone": 1938695106
}
         */

        //using RESPONSE
       Spartan spartan = response.as(Spartan.class);
        System.out.println("spartan = " + spartan);
        System.out.println("spartan.getId() = " + spartan.getId());
        System.out.println("spartan.getName() = " + spartan.getName());
        System.out.println("spartan.getGender() = " + spartan.getGender());
        System.out.println("spartan.getPhone() = " + spartan.getPhone());

//Using JSONPATH
        //create jsonPath object from response:
      JsonPath jsonPath = response.jsonPath();

      //create spartan from jsonpath:
        Spartan sp = jsonPath.getObject("", Spartan.class);
        System.out.println("sp = " + sp);
        System.out.println("sp.getId() = " + sp.getId());
        System.out.println("sp.getName() = " + sp.getName());
        System.out.println("sp.getGender() = " + sp.getGender());
        System.out.println("sp.getPhone() = " + sp.getPhone());

    }
    @DisplayName("Get Spartans from search endpoint for deserilization to POJO")
    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON)

                .when().get("/api/spartans/search")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract().response();


        //using RESPONSE
        System.out.println("=====Response get 1st Spartan=====");
        //Spartan spartan = response.as(Spartan.class);--> since we can not put path in here to get specific part of response
        //--> we are not going to do it


//Using JSONPATH : more powerful than response
        System.out.println("=====JsonPath get 1st Spartan=====");
        //create jsonPath object from response:
        JsonPath jsonPath = response.jsonPath();

        //create spartan from jsonpath:
        Spartan sp = jsonPath.getObject("content[0]", Spartan.class);
        System.out.println("sp = " + sp);


    }

    @DisplayName("Get Spartans from search endpoint for deserilization to Search class")
    @Test
    public void test3(){
        Response response = given().accept(ContentType.JSON)

                .when().get("/api/spartans/search")
                //.prettyPeek()
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("=====Response get 1st Spartan=====");
        Search search1 = response.as(Search.class);
  //since we are not provide path for responce still we can use response.as to make deserilization

        System.out.println("=====Json get 1st Spartan=====");

        JsonPath js = response.jsonPath();
      Search search= js.getObject("", Search.class);

        System.out.println("search.getTotalElement() = " + search.getTotalElement());
        System.out.println("search.getContent().get(0) = " + search.getContent().get(0));

        System.out.println("search.getContent().get(0).getName() = " + search.getContent().get(0).getName());

        System.out.println("search.getContent().get(1).getGender() = " + search.getContent().get(1).getGender());


    }

    @DisplayName("Get Spartans from search endpoint for deserilization to List of Spartan class")
    @Test
    public void test4() {
        Response response = given().accept(ContentType.JSON)

                .when().get("/api/spartans/search")
                //.prettyPeek()
                .then()
                .statusCode(200)
                .extract().response();

 JsonPath jsonPath = response.jsonPath();

List<Spartan> allSpartans =  jsonPath.getList("content", Spartan.class);
for(Spartan eachSpartan : allSpartans){
    System.out.println("eachSpartan = " + eachSpartan);
}

        System.out.println("allSpartans.get(0) = " + allSpartans.get(0));
        System.out.println("allSpartans.get(0).getId() = " + allSpartans.get(0).getId());
    }
}
