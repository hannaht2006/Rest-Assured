package com.cydeo.pojo.cydeoTrainingPojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {
    private String companyName;
    private Address address;
}
