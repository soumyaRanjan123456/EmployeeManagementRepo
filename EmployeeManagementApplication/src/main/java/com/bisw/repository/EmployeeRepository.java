package com.bisw.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bisw.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
