package com.bisw.service;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bisw.entity.Employee;
import com.bisw.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Store employee details.
     */
    public Employee saveEmployee(Employee employee) {
        // Additional validation logic if required
        return employeeRepository.save(employee);
    }

    /**
     * Calculate tax deductions for an employee.
     */
    public TaxDeductionResponse calculateTaxDeductions(String employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalEmployee.isEmpty()) {
            throw new RuntimeException("Employee not found with ID: " + employeeId);
        }

        Employee employee = optionalEmployee.get();

        // Calculate yearly salary considering DOJ (Date of Joining)
        double yearlySalary = calculateYearlySalary(employee);

        // Calculate tax
        double taxAmount = calculateTax(yearlySalary);

        // Additional 2% cess for income above 2,500,000
        double cess = yearlySalary > 2500000 ? 0.02 * (yearlySalary - 2500000) : 0;

        return new TaxDeductionResponse(employeeId, employee.getLastName(), yearlySalary, taxAmount, cess);
    }

    private double calculateYearlySalary(Employee employee) {
        LocalDate doj = employee.getDoj();
        LocalDate today = LocalDate.now();

        if (doj.isAfter(today)) {
            throw new IllegalArgumentException("Date of Joining cannot be in the future.");
        }

        int monthsWorked = Math.toIntExact(ChronoUnit.MONTHS.between(doj.withDayOfMonth(1), today.withDayOfMonth(1))) + 1;

        return (employee.getSalary() / 12) * monthsWorked;
    }

    private double calculateTax(double yearlySalary) {
        double tax = 0;

        if (yearlySalary > 1000000) {
            tax += (yearlySalary - 1000000) * 0.20;
            yearlySalary = 1000000;
        }
        if (yearlySalary > 500000) {
            tax += (yearlySalary - 500000) * 0.10;
            yearlySalary = 500000;
        }
        if (yearlySalary > 250000) {
            tax += (yearlySalary - 250000) * 0.05;
        }

        return tax;
    }

}
