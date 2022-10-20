package com.endava.webapp.domain.dto;

import com.endava.webapp.domain.models.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    public Department toDepartment() {
        return Department.builder()
                .name(name)
                .location(location)
                .build();
    }
}
