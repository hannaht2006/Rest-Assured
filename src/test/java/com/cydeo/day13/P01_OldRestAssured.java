package com.cydeo.day13;

import com.cydeo.utilities.SpartanNewTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class P01_OldRestAssured extends SpartanNewTestBase {

    @Test
    public void getAllSpartan(){
        given().accept(ContentType.JSON)
                .auth().basic("admin", "admin")
           .when().get("/spartans")
                .prettyPeek()
           .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id[0]",is(101))
                .body("id[2]", is(103))
        ;
    }

    @Test
    public void getAllSpartanOldWay(){
        given().accept(ContentType.JSON)
                .auth().basic("admin", "admin")
         .expect().statusCode(200)
                .contentType(ContentType.JSON)
                .body("id[0]",is(1))
                .body("id[2]", is(103))
          .when().get("/spartans")
                .prettyPeek();
    }

    /**
     * OLD WAY --> EXPECT()
     *  it work like soft assert
     *
     *  NEW WAY --> then() (this is the way that we are gonna use now also in the future. it is what we learn so far.
     *  it works like hard assertion
     */

}
