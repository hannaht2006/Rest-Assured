package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormulaDriver {

    private String driverId;
     private String permanentNumber;
     private String code;
     private String url;
     private String givenName;
     private String  familyName;
     private String dateOfBirth;
     private String  nationality;
}
