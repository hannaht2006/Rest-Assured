package com.cydeo.day8;

import com.cydeo.utilities.NewsAPITestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class NewAPIReview extends NewsAPITestBase {
    /*
TASK 1 —> QUERY PARAM
- Given query param is q=“bitcoin”
 - And query param is apiKey=“yourKey”
 - When user sent request / everything endpoint
- Then status code should be 200
- And each articles contains “bitcoin”
 */
    @Test
    public void Test(){
        given().log().uri().accept(ContentType.JSON)
                .queryParam("q", "bitcoin")
                .queryParam("apikey", "f83c7864f4024e21a064bf59f5d992b1")
                .when().get("/everything").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON);
    }
/*
   TASK 2 —> X-Api-Key in HEADER
- Given query param is q=“bitcoin”
 - And header is X-Api-Key=“yourKey”
 - When user sent request / everything endpoint
- Then status code should be 200
- And each articles contains “bitcoin”
    */
    @Test
    public void test2(){
        given().accept(ContentType.JSON)
                .queryParam("q","bitcoin")
                .header("X-Api-Key", "f83c7864f4024e21a064bf59f5d992b1")
                .when().get("/everything").prettyPeek()
                .then().statusCode(200);

    }

    /*
   TASK 3 —> Authorization in HEADER
- Given query param is q=“bitcoin”
 - And header is Authorization=“yourKey”
 - When user sent request / everything endpoint
- Then status code should be 200
- And each articles contains “bitcoin”

    */
    @Test
    public void test3(){
        given().accept(ContentType.JSON)
                .queryParam("q", "bitcoin")
                .header("authorization", "f83c7864f4024e21a064bf59f5d992b1")
                .when().get("/everything").prettyPeek()
                .then().statusCode(200);
    }
/*
   TASK 4 —> Authorization in HEADER
- Given query param is country=“us”
 - And header is Bearer=“yourKey”
 - When user sent request / top-headlines endpoint
- Then status code should be 200
- And print out all sources names
    */
    @Test
    public void test4(){
        given().accept(ContentType.JSON)
                .queryParam("country", "us")
                .header("authorization", "Bearer f83c7864f4024e21a064bf59f5d992b1")
                .when().get("/top-headlines").prettyPeek()
                .then().statusCode(200);
    }
}
