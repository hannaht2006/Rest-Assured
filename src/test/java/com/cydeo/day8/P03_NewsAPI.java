package com.cydeo.day8;

import com.cydeo.utilities.NewsAPITestBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class P03_NewsAPI extends NewsAPITestBase {
/*
TASK 1 —> QUERY PARAM
- Given query param is q=“bitcoin”
 - And query param is apiKey=“yourKey”
 - When user sent request / everything endpoint
- Then status code should be 200
- And each articles contains “bitcoin”
 */
   @Test
   public void test1(){

    //   String apiKey ="d76cdd6edd704269a89f9742c91c75c4";
       given().queryParam("q", "bitcoin")
               .queryParam("apiKey", "d76cdd6edd704269a89f9742c91c75c4")
               .when().get("/everything")
               .prettyPeek()
               .then()
               .statusCode(200);

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

       //   String apiKey ="d76cdd6edd704269a89f9742c91c75c4";
       given().queryParam("q", "bitcoin")
               .header("X-Api-Key", "d76cdd6edd704269a89f9742c91c75c4")
               .when().get("/everything")
               .prettyPeek()
               .then()
               .statusCode(200);

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

       //   String apiKey ="d76cdd6edd704269a89f9742c91c75c4";
       given().log().uri().
               queryParam("q", "bitcoin")
               .header("authorization", "d76cdd6edd704269a89f9742c91c75c4")
               .when().get("/everything")
               .prettyPeek()
               .then()
               .statusCode(200);

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

        //   String apiKey ="d76cdd6edd704269a89f9742c91c75c4";
        given().log().uri().
                queryParam("country", "us")
                .header("authorization", "Bearer d76cdd6edd704269a89f9742c91c75c4")
                .when().get("/top-headlines")
                .prettyPeek()
                .then()
                .statusCode(200);

    }
}
