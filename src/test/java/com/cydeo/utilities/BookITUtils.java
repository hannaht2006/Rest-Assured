package com.cydeo.utilities;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class BookITUtils {

    static public RequestSpecification reqResSpec;
    static public ResponseSpecification resSpec;

    public static String getToken(String email, String password){
     String accessToken  = given().accept(ContentType.JSON)
                .queryParam("email", email)
                .queryParam("password", password)
                .when().get("/sign")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("accessToken");

     assertThat(accessToken, not(emptyOrNullString()));
        return "Bearer " + accessToken;
    }
    /*
    How to build connection here

    Before all will init BASEURI since it is static field for RestAssured
    when we send any request it will use as BASEURI --> BOOKITURI
     */

    /*
    if change role users to different user --> change email, password
    and will have different token for that type of user

    we can create each getToken class for each user type as well

    public static String getTokenByRole(String usename, String password){
    String email;
    String password;
    switch(role){
    case "team-leader":
    email = ConfigurationReader.getProperty("team-leader-email';
    password:
    break;
    case "team-member"
    .
    .
    .
    email and password after switch

    String accessToken  = given().accept(ContentType.JSON)
                .queryParam("email", email)
                .queryParam("password", password)
                .when().get("/sign")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("accessToken");

     assertThat(accessToken, not(emptyOrNullString()));
        return "Bearer " + accessToken;
    }

     */

    public static String getTokenByRole(String role) {
        String email = "";
        String password = "";

        switch (role) {
            case "teacher":
                email = ConfigurationReader.getProperty("teacher_email");
                password = ConfigurationReader.getProperty("teacher_password");
                break;

            case "student-member":
                email = ConfigurationReader.getProperty("team_member_email");
                password = ConfigurationReader.getProperty("team_member_password");
                break;
            case "student-leader":
                email = ConfigurationReader.getProperty("team_leader_email");
                password = ConfigurationReader.getProperty("team_leader_password");
                break;
            default:

                throw new RuntimeException("Invalid Role Entry :\n>> " + role +" <<");
        }

        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        String accessToken = given()
                .queryParams(credentials)
                .when().get( "/sign").path("accessToken");

        return  "Bearer " + accessToken;

    }

    public static RequestSpecification getReqSpec(String role){

        RequestSpecification reqSpec = given()
                .log().all()
                .header("Authorization", getTokenByRole(role))
                .accept(ContentType.JSON);

        return reqSpec;
    }

    public static ResponseSpecification getResSpec(int statusCode){
        return
                expect()
                        .log().all()
                        .statusCode(statusCode)
                        .contentType(ContentType.JSON);

    }
}
