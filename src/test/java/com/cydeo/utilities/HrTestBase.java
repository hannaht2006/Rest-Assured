package com.cydeo.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class HrTestBase {
    @BeforeAll
    public static void init() {

        RestAssured.baseURI = "http://44.212.37.188:1000/ords/hr";

        //yourIP:100/ords/hr""
    }
}
