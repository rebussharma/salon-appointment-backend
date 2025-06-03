package com.sammysbrow.backend.controller;

import com.sammysbrow.backend.dto.EmployeeDto;
import com.sammysbrow.backend.service.employee.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    // Add Employee REST Api
    // RequestBody will extract Json from HTTP request and convert it into EmployeeDto object
    // @Valid ensures the request payload is validated against the constraints defined in EmployeeDto
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }
    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employeeDto = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeDto);
    }



}
