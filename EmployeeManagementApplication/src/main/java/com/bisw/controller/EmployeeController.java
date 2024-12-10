package com.bisw.controller;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bisw.entity.Employee;
import com.bisw.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;

    @PostMapping
    public ResponseEntity<?> saveEmployee(@Validated @RequestBody Employee employee) {
        if (repository.existsById(employee.getEmployeeId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Employee ID must be unique");
        }
        repository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body("Employee saved successfully");
    }

    @GetMapping("/{employeeId}/tax-deductions")
    public ResponseEntity<?> getTaxDeductions(@PathVariable String employeeId) {
        Employee employee = repository.findById(employeeId).orElse(null);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee not found");
        }

        double yearlySalary = calculateYearlySalary(employee.getDoj(), employee.getSalary());
        double tax = calculateTax(yearlySalary);

        Map<String, Object> response = new HashMap<>();
        response.put("employeeId", employee.getEmployeeId());
        response.put("lastName", employee.getLastName());
        response.put("yearlySalary", yearlySalary);
        response.put("taxAmount", tax);

        return ResponseEntity.ok(response);
    }

    private double calculateYearlySalary(LocalDate doj, double monthlySalary) {
        int monthsWorked = YearMonth.now().getMonthValue() - doj.getMonthValue() + 1;
        return monthsWorked * monthlySalary;
    }

    private double calculateTax(double yearlySalary) {
        double tax = 0;

        if (yearlySalary > 250000) {
            tax += Math.min(yearlySalary - 250000, 250000) * 0.05;
        }
        if (yearlySalary > 500000) {
            tax += Math.min(yearlySalary - 500000, 500000) * 0.10;
        }
        if (yearlySalary > 1000000) {
            tax += (yearlySalary - 1000000) * 0.20;
        }

        if (yearlySalary > 2500000) {
            tax += (yearlySalary - 2500000) * 0.02;
        }

        return tax;
    }
}
