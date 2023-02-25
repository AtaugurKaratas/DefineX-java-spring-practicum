package com.example.credit.repository;

import com.example.credit.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> getEmployeeByAuthId(String authId);
}
