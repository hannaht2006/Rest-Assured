package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class Homework02 extends HrTestBase {

    /*
    TASK 1 :
- Given accept type is Json
- Path param value- US
- When users sends request to /countries
- Then status code is 200
- And Content - Type is Json
- And country_id is US
- And Country_name is United States of America
- And Region_id is 2
     */
    @DisplayName("GET request to countries with using path params")
    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("country_id", "US")
                .when().get("/countries/{country_id}");

response.prettyPrint();

        //- Then status code is 200
        assertEquals(200, response.statusCode());

        //- And Content - Type is Json
        assertEquals("application/json", response.contentType());
        //- And country_id is US
        assertEquals("US", response.path("country_id"));
        //- And Country_name is United States of America
        assertEquals("United States of America", response.path("country_name"));
        //- And Region_id is 2
        assertEquals("2", response.path("region_id").toString());
    }

/*
TASK 2 :
- Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all job_ids start with 'SA'
- And all department_ids are 80
- Count is 25
 */
@DisplayName("GET request to employees with using query params")
@Test
public void test2() {
    Response response = given().accept(ContentType.JSON)
                      .and()
                      .queryParam("q", "{\"department_id\":80}")
                      .when().get("/employees");

//- Then status code is 200
   assertEquals(200, response.statusCode());
//- And Content - Type is Json
    assertEquals("application/json", response.contentType());
//- And all job_ids start with 'SA'
    List<String> allJobId =response.path("items.job_id");

    for(String each :allJobId){
        assertTrue(each.startsWith("SA"));
    }
//- And all department_ids are 80
    List<Integer> allDepartmentId = response.path("items.department_id");
    for (int each : allDepartmentId){
        assertEquals(80, each);
    }
//- Count is 25
 assertEquals("25", response.path("count").toString());
}
/*
TASK 3 :
- Given accept type is Json
-Query param value q= {"region_id":3}
- When users sends request to /countries
- Then status code is 200
- And all regions_id is 3
- And count is 6
- And hasMore is false
- And Country_name are;
Australia,China,India,Japan,Malaysia,Singapore
 */
@DisplayName("GET request to countries with using query params")
@Test
public void test3() {
    Response response = given().accept(ContentType.JSON)
            .and()
            .queryParam("q", "{\"region_id\":3}")
            .when().get("/countries");

    //- Then status code is 200
    assertEquals(200, response.statusCode());

    //- And all regions_id is 3
    List<Integer> allRegionId = response.path("items.region_id");
    for(int each : allRegionId){
        assertEquals(3, each);
    }
    //- And count is 6
    assertEquals("6", response.path("count").toString());

    //- And hasMore is false
    JsonPath jsonPath = response.jsonPath();

    assertFalse(jsonPath.getBoolean("hasMore"));

    //- And Country_name are;
    //Australia,China,India,Japan,Malaysia,Singapore
   List<String> allCountryName = jsonPath.getList("items.findAll{it.region_id}.country_name");

   List<String> countries=new ArrayList<>(Arrays.asList("Australia","China", "India", "Japan", "Malaysia", "Singapore" ));
  assertTrue(allCountryName.containsAll(countries));
}
}





