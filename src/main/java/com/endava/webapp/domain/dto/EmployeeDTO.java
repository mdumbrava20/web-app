package com.endava.webapp.domain.dto;

import com.endava.webapp.domain.models.Department;
import com.endava.webapp.domain.models.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private long departmentId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String phoneNumber;

    @DecimalMin("1")
    private double salary;

    public Employee toEmployee(Department department) {
        return Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .email(email)
                .salary(salary)
                .department(department)
                .build();
    }
}
