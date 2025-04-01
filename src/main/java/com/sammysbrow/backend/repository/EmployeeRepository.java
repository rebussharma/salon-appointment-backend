package com.sammysbrow.backend.repository;

import com.sammysbrow.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/*
    Since this is a repository, we'd usually annotate this class with @Repository but we dont need to do that
            as another class "SimpleJpaRepository" that JpaRepository implements already uses the @Repository annotation.

     Same goes for @Transactional annotation as JpaRepository already uses it
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
