package com.sammysbrow.backend.dto.appointment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceType {
    private Long id;
    private Long mainServiceId;
    private String name;
    private Double price;
    private Integer duration; // Duration in minutes
}

