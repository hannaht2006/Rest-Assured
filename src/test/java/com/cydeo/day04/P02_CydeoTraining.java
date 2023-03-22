package com.cydeo.day04;

import com.cydeo.utilities.CydeoTrainingTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class P02_CydeoTraining extends CydeoTrainingTestBase {

    @DisplayName("Get student/{id} 2")
    @Test
    public void test1() {
/*
    Given accept type is application/json
    And path param is 2
    When user send request /student/{id}
    Then status code should be 200
    And content type is application/json;charset=UTF-8
    And Date header is exist
    And Server header is enjoy
    And verify following
                firstName Mark
                batch 13
                major math
                emailAddress mark@email.com
                companyName Cydeo
                street 777 5th Ave
                zipCode 33222



    "students": [
        {
            "studentId": 2,    --> students[0].firstName
            "firstName": "Mark",
            "lastName": "Telesco",
            "batch": 13,
            "joinDate": "12/27/2021",
            "birthDate": "02/04/2001",
            "password": "4333333",
            "subject": "student",
            "gender": "Male",
            "admissionNo": "123",
            "major": "math",
            "section": "100000",
            "contact": {
                "contactId": 1,
                "phone": "1235332544",
                "emailAddress": "mark@email.com",
                "permanentAddress": "123 happy street"
            },
            "company": {
                "companyId": 2,
                "companyName": "Cydeo",
                "title": "Dean",
                "startDate": "06/19/2018",
                "address": {
                    "addressId": 2,
                    "street": "777 5th Ave",
                    "city": "McLean",
                    "state": "Virginia",
                    "zipCode": 33222

                         firstName Mark               ---> students[0].firstName
         batch 13                     ---> students[0].batch
         major math                   ---> students[0].major
         emailAddress mark@email.com  ---> students[0].contact.emailAddress
         companyName Cydeo            ---> students[0].company.companyName
         street 777 5th Ave           ---> students[0].company.address.street
         zipCode 33222                ---> students[0].company.address.zipCode

     */
//we can use log().all() to see all info that you sending request; or you can use log().uri() to see just uri
        Response response = given().log().all().accept (ContentType.JSON)
                .and()
                .pathParam("id", 2)
                .when().get("/student/{id}");

        response.prettyPrint();

        assertEquals(200, response.statusCode());

        assertEquals("application/json;charset=UTF-8", response.contentType());

        assertTrue(response.headers().hasHeaderWithName("Date"));

        assertEquals("envoy", response.header("server"));

//start

        // Create JSON PATH OBJECT
        JsonPath jsonPath = response.jsonPath();

        assertEquals("Mark",jsonPath.getString("students[0].firstName"));


        assertEquals(13,jsonPath.getInt("students[0].batch"));

        assertEquals("math",jsonPath.getString("students[0].major"));

        assertEquals("mark@email.com",jsonPath.getString("students[0].contact.emailAddress"));

        assertEquals("Cydeo",jsonPath.getString("students[0].company.companyName"));

        assertEquals("777 5th Ave",jsonPath.getString("students[0].company.address.street"));

        assertEquals(33222,jsonPath.getInt("students[0].company.address.zipCode"));

    }
@DisplayName("student")
    @Test
    public void test2(){
        /*

    Given accept type is application/json
    And path param is 22
    When user send request /student/batch/{batch}
    Then status code should be 200
    And content type is application/json;charset=UTF-8
    And Date header is exist
    And Server header is envoy
    And verify all the batch number is 22

         */
    Response response = given().accept(ContentType.JSON)
            .and()
            .pathParam("batch", 22)
            .when().get("/student/batch/{batch}");

//    Then status code should be 200
    assertEquals(200, response.statusCode());

//    And content type is application/json;charset=UTF-8
    assertEquals("application/json;charset=UTF-8", response.contentType());

//    And Date header is exist
    assertTrue(response.headers().hasHeaderWithName("Date"));

//    And Server header is envoy
    assertEquals("envoy", response.header("Server"));

//    And verify all the batch number is 22
    List<Integer> allBatch = response.path("students.batch");
    for(int each : allBatch){
        assertEquals(22, each);
    }
}
}
