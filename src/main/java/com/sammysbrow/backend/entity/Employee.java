package com.sammysbrow.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity // Annotate class as JPA entity which will be its own table in the db
@Table(name = "employees") // name of table
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Uses db's auto increment to increase pk
    private Long id;

    @Column(name = "first_name") // firstName in this CODE will be mapped to first_name in the db
                                // If we skip this annotaiton, JPA will give column name as firstName in db too
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "hire_date")
    private Date hireDate;
    @Column(name = "employment_start_date")
    private Date employmentStartDate;
    @Column(name = "employment_end_date")
    private Date employmentEndDate;

}
