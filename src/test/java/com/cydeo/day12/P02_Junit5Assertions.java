package com.cydeo.day12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P02_Junit5Assertions {
    /**
     HARD ASSERT --> ASSERT
     *  - Test Execution will be aborted if the assert condition is not met
     *  - Rest of execution will stop
     *
     *  - Use Case --> if we are checking critical functionality of the app we can check with hard assert to continue next steps
     */
    @Test
    public void hardAssert(){
        assertEquals(10, 5+5);
        System.out.println("---first assert is done");
        assertEquals(9, 5+5);//this assertion is failed here, rest of assertions will stop here

        System.out.println("---second assert is done");
        assertEquals(10, 5+5);
        System.out.println("---third assert is done");
    }

    /**
     * SOFT ASSERT --> VERIFY (Soft assertion is implementation for verify)
     * -Test execution will continue till end of the code fragment even if one of the assertion is failing
     *
     * TESTNG --> SoftAssert softAssert = new SoftAssert()
     *              SoftAssert.assertEquals()
     *              SoftAssert.assertAll()....
     *
     */

    @DisplayName("JUNIT5 SOFT ASSERTION IS IMPLEMENTED")
    @Test
    public void softAssert() {

        assertAll("learning soft assert",
                () -> assertEquals(10, 5+5),
                ()-> assertEquals(9, 5+5),
                ()-> assertEquals(10, 5+5)

                );



    }

}
