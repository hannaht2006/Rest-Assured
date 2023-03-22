package com.cydeo.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class SpartanAuthTestBase {
    @BeforeAll
    public static void init() {

        RestAssured.baseURI = "http://3.80.111.193:7000";
    }
}
