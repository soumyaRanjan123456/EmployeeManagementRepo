package com.bisw.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Employee {

    @Id
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Invalid Employee ID format")
    private String employeeId;

    @NotNull
    @Size(min = 1, message = "First name cannot be empty")
    private String firstName;

    @NotNull
    @Size(min = 1, message = "Last name cannot be empty")
    private String lastName;

    @NotNull
    @Email(message = "Invalid email format")
    private String email;

    @ElementCollection
    @NotNull
    @Size(min = 1, message = "Phone numbers cannot be empty")
    private List<@Pattern(regexp = "\\d{10}", message = "Invalid phone number format") String> phoneNumbers;

    @NotNull
    private LocalDate doj;

    @NotNull
    @Min(value = 1, message = "Salary must be positive")
    private Double salary;

    
}
