package com.cydeo.practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class InterviewTask1 {

    /*
    Create Asset: POST /api/assets

Sample Request:
{
   "id":"ABC1234567",
   "fleetId":"fleet123",
   "hubId":"facility123",
   "organizationId":"organizationId",
   "name":"Road Sector",
   "hardwareId": "HARDWARE123",
   "type":{
      "model":"EP1",
      "version":"1.0"
   }
}
Sample Response:
{
   "success":true,
   "targetUrl":null,
   "unauthorizedRequest":false,
   "result":{
      "id":"ABC1234567",
      "fleetId":"fleet123",
      "hubId":null,
      "organizationId":"organizationId",
      "name":"Road Sector",
      "type":{
         "model":"EP1",
         "version":"1.0"
      }
   },
   "error":null,
   "__bd":true
}
     */

    @Test
    public void interviewTest() {
        BodyRequest bodyRequest = new BodyRequest();
        Type type = new Type();
        type.setModel("EP1");
        type.setVersion("1.0");

        bodyRequest.setId("ABC1234567");
        bodyRequest.setFleetId("fleet123");
        bodyRequest.setHubId("facility123");
        bodyRequest.setOrganizationId("organizationId");
        bodyRequest.setName("Road Sector");
        bodyRequest.setHardwareId("HARDWARE123");
        bodyRequest.setTypes(type);

        System.out.println(bodyRequest);

        JsonPath jsonPath = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(bodyRequest)
                .when()
                .post("/api/assets")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        int idFromPost  = jsonPath.getInt("result.id");

    }
}
