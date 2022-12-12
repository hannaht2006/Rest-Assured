package com.cydeo.day07;

import com.cydeo.pojo.Student;
import com.cydeo.utilities.CydeoTrainingTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class P01_CydeoTrainingDeserilizationPOJO extends CydeoTrainingTestBase {

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
*/

        JsonPath jsonPath = given().log().all().accept(ContentType.JSON)
                .and()
                .pathParam("id", 2)
                .when().get("/student/{id}")
                .then()
                .statusCode(200)
                .extract().jsonPath();

        Student student = jsonPath.getObject("students[0]", Student.class);//the path to create the List (Students has only 1 object)

        System.out.println("student = " + student);

        assertEquals("Mark", student.getFirstName());
        assertEquals(13, student.getBatch());
        assertEquals("math", student.getMajor());

        assertEquals("mark@email.com",student.getContact().getEmailAddress());
        assertEquals("Cydeo", student.getCompany().getCompanyName());
        assertEquals("777 5th Ave", student.getCompany().getAddress().getStreet());
        assertEquals(33222, student.getCompany().getAddress().getZipCode());


    }


}