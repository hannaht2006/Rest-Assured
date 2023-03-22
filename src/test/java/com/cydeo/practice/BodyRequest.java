package com.cydeo.practice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BodyRequest {
    private String id;
    private String fleetId;
    private String hubId;
    private String organizationId;
    private String name;
    private String hardwareId;
    private Type types;

}
