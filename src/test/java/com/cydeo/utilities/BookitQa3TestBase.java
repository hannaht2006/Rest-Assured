package com.cydeo.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BookitQa3TestBase {
    @BeforeAll
    public static void init(){
        RestAssured.baseURI = "http://api.qa3.bookit.cydeo.com";
    }
}
