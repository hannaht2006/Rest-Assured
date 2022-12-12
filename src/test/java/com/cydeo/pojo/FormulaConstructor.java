package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormulaConstructor {

    private String constructorId;
    private String name;


}
