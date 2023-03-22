package com.cydeo.day11;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class P01_SpartanAuthTest extends SpartanAuthTestBase {


    /**
     * Given accept type is application/xml
     * when send the request /api/spartans
     * then status code is 200
     * and content type is application/xml
     * print firstname
     *
     * ..
     */

    @Test
    public void test1(){
        given().accept(ContentType.XML)
                .auth().basic("admin", "admin")
                .when().get("/api/spartans")
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body("List.item[0].name", is("John Dan"))
                .body("List.item[0].gender", is("Male"));

    }

    @DisplayName("GET/api/spartans with using SMLPath")
    @Test
    public void test2(){
        Response response= given().accept(ContentType.XML)
                .auth().basic("admin", "admin")
                .when().get("/api/spartans");

        //GET response as XML format and save into XMLPath
       XmlPath xmlPath = response.xmlPath();

       //GET first spartan name
        System.out.println("xmlPath.getString(\"List.item[0].name\") = " + xmlPath.getString("List.item[0].name"));

        //GET 2nd spartan name
        System.out.println("xmlPath.getString(\"List/itme[1].name\") = " + xmlPath.getString("List/itme[1].name"));

        //GET last spartan name

        System.out.println("xmlPath.getString(\"List.item[-1].name\") = " + xmlPath.getString("List.item[-1].name"));


        //get all spartan name
        List<String> nameList = xmlPath.getList("List.item.name");
        System.out.println("nameList = " + nameList);


        //how many Spartan we have

     List<Spartan> allSpartans  = xmlPath.getList("List.item");
     //Deserilazation still possible to do it. We should use another dependency or use some java logic to store in to POJO
        //but we will not gonna touch this

        System.out.println("allSpartans.size() = " + allSpartans.size());
/**
 * WE know how many spartan we have
 * then we can create a loop?
 * yes
 * String spartanName = xmlPath.
 */
    }
}
