package com.cydeo.day11;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
public class P03_MovieXML {

    @Test
    public void test1(){
        Response response = given()
                .queryParam("t", "Superman")
                .queryParam("r", "xml")
                .queryParam("apikey", "81815fe6")
            .when().get("http://www.omdbapi.com").prettyPeek();

        //create XMLPath
        XmlPath xmlPath = response.xmlPath();

        //get year attribute
        System.out.println("xmlPath.getString(\"root.movie.@year\") = " + xmlPath.getString("root.movie.@year"));
        //get year title attribute

        System.out.println("xmlPath.getString(\"root.movie.@title\") = " + xmlPath.getString("root.movie.@title"));

        //get  genre attribute

        System.out.println("xmlPath.getString(\"root.movie.@genre\") = " + xmlPath.getString("root.movie.@genre"));

        //get year writer attribute

        System.out.println("xmlPath.getString(\"root.movie.@writer\") = " + xmlPath.getString("root.movie.@writer"));
    }

    /**
     *
     * Homework
     * http://www.omdbapi.com/apikey
     *
     *
     */
}
