package com.cydeo.officeHours;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import com.cydeo.utilities.SpartanUtil;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;


import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static java.util.Map.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpartansFlow1 extends SpartanTestBase {

    static int createdSpartanId;
    static String createdSpartanName;
    static Spartan spartan;

    static String putName;

    @Order(value = 1)
    @Test
    public void postTest1(){
        Spartan spartan = SpartanUtil.createSpartan();
       Response response = SpartanUtil.postSpartan(spartan);

       assertEquals(201, response.statusCode());
       assertEquals("A Spartan is Born!", response.jsonPath().getString("success"));

       createdSpartanName = spartan.getName();
        createdSpartanId = response.jsonPath().getInt("data.id");

    }
@Order(value = 2)
    @Test
    public void getCreatedSpartan(){
     Response response   = SpartanUtil.getSpartan(createdSpartanId);
       assertEquals(200, response.statusCode());
       assertEquals(createdSpartanName, response.jsonPath().getString("data.name"));


    }
    @Order(value = 3)
    @Test
    public void put() {
        Spartan updatedSpartan = new Spartan();
        putName ="Put "+ spartan.getName();
        updatedSpartan.setName(putName);
        updatedSpartan.setGender(spartan.getGender());
        updatedSpartan.setPhone(spartan.getPhone());

        Response response = SpartanUtil.updateSpartan(createdSpartanId, updatedSpartan);
        Assertions.assertEquals(204,response.statusCode());

    }

    @Order(value = 4)
    @Test
    public void getUpdatedSpartan() {
        Response response = SpartanUtil.getSpartan(createdSpartanId);
        Assertions.assertEquals(200,response.statusCode());
        Assertions.assertEquals(putName,response.jsonPath().getString("name"));

    }

    /*
     - DELETE  Spartan with spartanID   /api/spartans/{id}
                 - verify status code 204
     */
    @Order(value = 5)
    @Test
    public void delete() {
        Response response = SpartanUtil.deleteSpartan(createdSpartanId);
        assertEquals(204,response.statusCode());
    }

    @Order(value = 6)
    @Test
    public void getDeletedSpartan() {
        Response response = SpartanUtil.getSpartan(createdSpartanId);
        assertEquals(404,response.statusCode());
    }
}
