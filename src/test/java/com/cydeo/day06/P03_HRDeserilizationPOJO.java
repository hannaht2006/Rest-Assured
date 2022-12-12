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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class P03_HRDeserilizationPOJO extends HrTestBase {

    @DisplayName("get regions to deserilization to POJO + LOMBOK JSON PROPERTY")

    @Test
    public void test1() {

        JsonPath jsonPath = get("/regions")
                .then()
                .statusCode(200)
                .extract().jsonPath();

        Region firstRegion = jsonPath.getObject("items[0]", Region.class);

        System.out.println("firstRegion.getLinks().get(0).getHref() = " + firstRegion.getLinks().get(0).getHref());
    }

    @DisplayName("get employees to deserilization to POJO + LOMBOK JSON PROPERTY")

    @Test
    public void test2() {

        JsonPath jsonPath = get("/employees")
                .then()
                .statusCode(200)
                .extract().jsonPath();

        Employee employee = jsonPath.getObject("items[0]", Employee.class);
        System.out.println("employee = " + employee);
    }

    /*
    Task:
    Given accept is application/json
    when send request to /regions endpoint
    then status should be 200
    verify that region ids are 1, 2, 3, 4
    verify that region name Europe, Americas, Asia, Middle East and Africa,
    verify that count is 4
    create regions POJO
    ignore fields that you don't need
     */
    @Test
    public void test3() {

        JsonPath jsonPath = given().accept(ContentType.JSON)
                .when().get("/regions")
                .then()
                .statusCode(200)
                .extract().jsonPath();

        List<Region> region = jsonPath.getList("items", Region.class);
        System.out.println("region = " + region);

        List<Integer> regionId = new ArrayList<>();
        List<String> regionName = new ArrayList<>();

        for(Region each : region){
            regionId.add(each.getRegionId());
            regionName.add(each.getRegionName());
        }
        System.out.println("regionId = " + regionId);
        System.out.println("regionName = " + regionName);

//verify that region ids are 1, 2, 3,4
        List<Integer> expectedRegionIds = Arrays.asList(1, 2, 3, 4);
assertEquals(expectedRegionIds, regionId);

//    verify that region name Euroupe, Americas, Asia, Middle East and Africa,
        List<String> expectefRegionNames = Arrays.asList("Europe", "Americas", "Asia", "Middle East and Africa");
assertEquals(expectefRegionNames, regionName);

//    verify that count is 4
        assertEquals(4, region.size());
    }
}