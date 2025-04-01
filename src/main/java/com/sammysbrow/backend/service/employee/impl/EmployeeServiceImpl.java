package com.sammysbrow.backend.service.employee.impl;

import com.sammysbrow.backend.dto.EmployeeDto;
import com.sammysbrow.backend.entity.Employee;
import com.sammysbrow.backend.mapper.EmployeeMapper;
import com.sammysbrow.backend.repository.EmployeeRepository;
import com.sammysbrow.backend.service.employee.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service // Tells spring to create bean for current class
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        // We need to store employee JPA entity in code to database.
        // So we convert the EmployeeDto object to Employee JPA entity

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);

        // savedEmployee is the employee code reads, need to convert this to emplyeedto and return to client
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(
                EmployeeMapper::mapToEmployeeDto
        ).collect(Collectors.toList());
    }

}
