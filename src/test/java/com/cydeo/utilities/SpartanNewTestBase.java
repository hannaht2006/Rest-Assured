package com.cydeo.utilities;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

public abstract class SpartanNewTestBase {

    static public RequestSpecification reqResSpec;// this has to be static because the method that the field in is static (@BeforeAll  public static void init(){ ) and public to be called in other class

    static public RequestSpecification reqUserSpec;
    static public ResponseSpecification resSpec;
    @BeforeAll
    public static void init(){
       baseURI = "http://44.212.37.188";
       port = 8000;
       basePath ="/api";
       //baseURI+port+basePath

         reqResSpec = given().log().all().accept(ContentType.JSON)
                .auth().basic("admin", "admin");

        reqUserSpec = given().log().all().accept(ContentType.JSON)
                .auth().basic("user", "user");

         resSpec = expect().statusCode(200)
                .contentType(ContentType.JSON);
    }

    public static RequestSpecification dynamicReqSpec(String username, String password){

        return
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .auth().basic(username, password);

    }

    public static ResponseSpecification dynamicResSpec(int statusCode){

        return
                expect()
                        .log().all()
                        .statusCode(statusCode)
                        .contentType(ContentType.JSON);

    }
}
