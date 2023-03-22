package com.cydeo.day11;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class P02_ResponseTimeTest extends SpartanAuthTestBase {

    @DisplayName("Get/ api/spartans to get response time")
    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .when().get("/api/spartans")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                // .time(lessThanOrEqualTo(500l))
                .time(both(greaterThan(200l)).and(lessThanOrEqualTo(800l))).extract().response();//response will store information about response time as well
//so it is actual
        //response time should be less than, or in the range
        //(both(greaterThan(200l)).and(lessThanOrEqualTo(800l)))

        System.out.println("response.getTimeIn(TimeUnit.MICROSECONDS) = " + response.getTimeIn(TimeUnit.MICROSECONDS));

        //or
        System.out.println("response.getTimeIn(TimeUnit.NANOSECONDS) = " + response.getTimeIn(TimeUnit.NANOSECONDS));

    }
}
