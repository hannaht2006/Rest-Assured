package com.cydeo.day06;

import com.cydeo.pojo.Employee;
import com.cydeo.pojo.Region;
import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class P03_HRDeserilizationPOJO extends HrTestBase {

@DisplayName("get regions to deserilization to POJO + LOMBOK JSON PROPERTY")

@Test
    public void test1(){

   JsonPath jsonPath= get("/regions")
           .then()
           .statusCode(200)
           .extract().jsonPath();

 Region firstRegion = jsonPath.getObject("items[0]", Region.class);

    System.out.println("firstRegion.getLinks().get(0).getHref() = " + firstRegion.getLinks().get(0).getHref());
}

    @DisplayName("get employees to deserilization to POJO + LOMBOK JSON PROPERTY")

    @Test
    public void test2(){

        JsonPath jsonPath= get("/employees")
                .then()
                .statusCode(200)
                .extract().jsonPath();

     Employee employee =   jsonPath.getObject("items[0]", Employee.class);
        System.out.println("employee = " + employee);
    }


}
