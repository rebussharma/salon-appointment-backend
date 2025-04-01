package com.sammysbrow.backend.mapper;

import com.sammysbrow.backend.dto.EmployeeDto;
import com.sammysbrow.backend.entity.Employee;

public class EmployeeMapper {
    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getHireDate(),
                employee.getEmploymentStartDate(),
                employee.getEmploymentEndDate()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getHireDate(),
                employeeDto.getEmploymentStartDate(),
                employeeDto.getEmploymentEndDate()
        );
    }
}
