package com.cydeo.day8;

import com.cydeo.utilities.BookITUtils;
import com.cydeo.utilities.BookItTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class P02_BookItTest extends BookItTestBase {

   // String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMTMxMiIsImF1ZCI6InN0dWRlbnQtdGVhbS1sZWFkZXIifQ.ZIHJuDh19eLga3bLP7udnvNtEA0DM_W1H67ah2Zu3Lc";

    String email = "lfinnisz@yolasite.com";
    String password = "lissiefinnis";
    String accessToken = BookITUtils.getToken(email, password);//using getToken method from BookITUtil class

    @DisplayName("GET api/campuses")
    @Test
    public void test1(){

given().accept(ContentType.JSON)
        .header("Authorization",accessToken )
        .when().get("/api/campuses")
        .prettyPeek()
        .then()
        .statusCode(200);
    }
//create new Util class and it will generate token based on your email and password
    //BookITUtils.getToken(String username, String password)


    @DisplayName("GET /api/users/me")
    @Test
    public void test2() {

        System.out.println("accessToken = " + accessToken);
        given().accept(ContentType.JSON)
                .header("Authorization", accessToken)
                .when().get("/api/users/me")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @DisplayName("GET /api/users/me")
    @Test
    public void test3() {

        System.out.println("accessToken = " + accessToken);
        given().accept(ContentType.JSON)
                .auth().oauth2(accessToken)
             //   .header("Authorization", accessToken)
                .when().get("/api/users/me")
                .prettyPeek()
                .then()
                .statusCode(200);
    }


}
