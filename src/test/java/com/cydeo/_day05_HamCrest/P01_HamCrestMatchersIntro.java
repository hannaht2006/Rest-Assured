package com.cydeo._day05_HamCrest;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class P01_HamCrestMatchersIntro {

    @Test
    public void numbers(){

        //assertion from Junit 5
        assertEquals(9, 3+6);

        //Hamcrest matcher assertion come from Rest-assured dependency
        //import static org.hamcrest.MatcherAssert.*;
        //import static org.hamcrest.Matchers.*;
        //adding the following static import we are not gonna use classnames while e are calling method from related classes
        //Matchers have 2 overload methods:
        //first one will take value to check
        //second one will take another Matcher to make it readable/ to add new assert functionality
        assertThat(9, is(3+6));

        assertThat(9, is(equalTo(6+3)));

        assertThat(9, equalTo(6+3));
        /**
         * is(someValue)
         * is(equalTo(someValue))
         * equalTo(someValue)
         * all of them are the same in terms of assertion
         */

        assertThat(5+5, not(9));
        //or we can write
        assertThat(9, is(not(5+5)));
        //or we can write
        assertThat(9, is(not(equalTo(5+5))));
        /**
         * three ways are the same
         */
        /**
         * greaterThan()  lessThan()
         * greaterThanOtEqualTo() lessThanOrEqualTo()
         */

     assertThat(6+5, is(greaterThan(10)));
     assertThat(5+5, greaterThan(9));
     assertThat(5+5, lessThanOrEqualTo(10));

    }
  @Test
  public void testString(){
        //first will be actual result in this case
      //yes for now. But while we are implementing them in then sections we are gonna send actual as we did in here
      //it will read data from response under the hood

        String msg = "API is fun!";

        assertThat(msg, is("API is fun!"));
        assertThat(msg, equalTo("API is fun!"));
        assertThat(msg, equalToIgnoringCase("api is fun!"));

    //startWith()
        assertThat(msg, startsWith("API"));
      assertThat(msg, startsWithIgnoringCase("api"));

      //endsWith()
      assertThat(msg, endsWith("fun!"));
      assertThat(msg, endsWithIgnoringCase("FUN!"));

      //containsString()
      assertThat(msg, containsString("is"));
      assertThat(msg, containsStringIgnoringCase("IS"));


      assertThat(msg, not("Fun"));
      assertThat(msg, is(not("fun")));

  }

  @Test
    public void collections(){
      List<Integer> numberList = Arrays.asList(3, 5, 1, 77, 44, 76);

      //check size of elements
      assertThat(numberList, hasSize(6));

      //check 44 is belong collection
      assertThat(numberList, hasItem(44));
      //check 44, 76 is into numberList
      assertThat(numberList, hasItems(44, 76));
      assertThat(numberList, hasItems(44, 76, 2));  //failed because don't have 2

      assertThat(numberList, hasItems(greaterThan(40)));

      //everyItem(): check each element in numberList with the condition
      assertThat(numberList, everyItem(greaterThan(5)));//failed

      //check with relative order:
     assertThat(numberList, containsInRelativeOrder(3, 5, 1, 77, 44, 76));


  }

}
