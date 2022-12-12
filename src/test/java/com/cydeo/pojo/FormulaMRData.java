package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormulaMRData {

    private String limit;
    private String total;
    private List<FormulaConstructor> ConstructorTable;

}
