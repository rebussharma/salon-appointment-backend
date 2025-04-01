package com.sammysbrow.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto { // Data Transfer Object. Used to transfer data between client and server
    private Long id;
    private String firstName;
    private String lastName;
    private Date hireDate;
    private Date employmentStartDate;
    private Date employmentEndDate;
}
