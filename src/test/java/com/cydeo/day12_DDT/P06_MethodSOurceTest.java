package com.cydeo.day12_DDT;

import com.cydeo.utilities.ExcelUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class P06_MethodSOurceTest {



    @ParameterizedTest
    @MethodSource("getNames")
    public void test1(String name){
        System.out.println("Name : " + name);

    }


    public static List<String> getNames() {
        List<String> nameList =  Arrays.asList("Kimberly", "King", "TJ", "Bond");
        return nameList;
        /**
         *can we read data from database:
         *  - Create connection / run query / store them and feed Parameterized
         *  -Can we get data from 3rd party APIs (That we consume to get data and use into our API)
         *  -use Java knowledge + RestAssured
         *
         *  this is make @methodsource more powerful that others
         *
         */
    }

@ParameterizedTest
@MethodSource("getExcelData")
public void credencialsTest(Map<String, String> userInfo){
    System.out.println(userInfo);

    System.out.println("userInfo.get(\"Email\") = " + userInfo.get("Email"));
    System.out.println("userInfo.get(\"Password\") = " + userInfo.get("Password"));

    System.out.println("------------" +
            "");
}
    public static List<Map<String, String>> getExcelData(){
        ExcelUtil library = new ExcelUtil("src/test/resources/Library.xlsx", "Library1-short");
        List<Map<String, String>> allUserInfo = library.getDataList();

        return library.getDataList();

    }
}
