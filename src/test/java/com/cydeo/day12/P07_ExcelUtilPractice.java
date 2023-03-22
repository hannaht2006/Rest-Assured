package com.cydeo.day12;

import com.cydeo.utilities.ExcelUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class P07_ExcelUtilPractice {

    @Test
    public void test1(){
        ExcelUtil library = new ExcelUtil("src/test/resources/Library.xlsx", "Library1-short");
        List<Map<String, String>> allUserInfo = library.getDataList();

        for(Map<String, String> eachUser : allUserInfo){
            System.out.println("eachUser : " + eachUser);
        }

    }


}
