package com.cydeo.day13;

import com.cydeo.utilities.SpartanNewTestBase;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;



public class P02_SpartanSpecTest extends SpartanNewTestBase {

    @Test
    public void getAllApartans(){

        given().log().all().accept(ContentType.JSON)
                .auth().basic("admin", "admin")
             .when().get("/spartans")
             .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void getAllApartansWithReqResSpec(){
//create RequestSpecification reqResSpec
        //and ResponseSpecification resSpec
        // for the common parts to short and organize the code:

        RequestSpecification reqResSpec = given().log().all().accept(ContentType.JSON)
                .auth().basic("admin", "admin");

        ResponseSpecification resSpec = expect().statusCode(200)
                        .contentType(ContentType.JSON);


        given().spec(reqResSpec)
                .when().get("/spartans")
                .then().spec(resSpec);
    }

    @Test
    public void getSignleApartansWithReqResSpec(){

        given().spec(reqResSpec)
                .pathParam("id", 3)
                .when().get("/spartans/{id}")
                .prettyPeek()
                .then().spec(resSpec)
                .body("id", is(3));
    }

    @Test
    public void getSignleApartansAsUser(){

        given().spec(dynamicReqSpec("user", "user"))
                .pathParam("id", 3)
                .when().get("/spartans/{id}")
                .then().spec(dynamicResSpec(200));
    }

    /**
     * create GET_RBAC.csv
     * admin, admin, 3, 200
     * editor, editor, 3, 200
     * user, user, 3, 200
     *
     *
     * create a parameterized test to check RBAC for GET method
     */

    @ParameterizedTest
    @CsvFileSource(resources = "/rbac.csv",numLinesToSkip = 1)

    public void getSignleApartansCsvFile(String username, String password, int id, int statuscode){
        given().spec(dynamicReqSpec(username, password))
                .pathParam("id", id)
                .when().get("/spartans/{id}")
                .then().spec(dynamicResSpec(statuscode));

    }

    /**
     * create RBAC_Delete.csv
     * admin, admin, 3, 200
     * editor, editor, 3, 200
     * user, user, 3, 200
     *
     *
     * create a parameterized test to check RBAC for DELETE method
     */

    @ParameterizedTest
    @CsvFileSource(resources = "/rbac_Delete.csv",numLinesToSkip = 1)

    public void deleteWithCsvFile(String username, String password, int id, int statuscode){

        given().spec(dynamicReqSpec(username, password))
                .pathParam("id", id)
                .when().delete("/spartans/{id}")
                .then().spec(dynamicResSpec(statuscode));


    }

}
