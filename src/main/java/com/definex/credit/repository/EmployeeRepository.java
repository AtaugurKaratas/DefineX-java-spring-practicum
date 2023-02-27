package com.definex.credit.repository;

import com.definex.credit.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> getEmployeeByAuthId(String authId);
}
