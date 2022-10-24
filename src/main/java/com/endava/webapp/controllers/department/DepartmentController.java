package com.endava.webapp.controllers.department;

import com.endava.webapp.domain.dto.DepartmentDTO;
import com.endava.webapp.domain.models.Department;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/departments")
public interface DepartmentController {

    @GetMapping()
    ResponseEntity<List<Department>> getAllDepartments();

    @GetMapping("/{id}")
    ResponseEntity<Department> getDepartmentById(@PathVariable() long id);

    @PostMapping()
    ResponseEntity<Department> addDepartment(@RequestBody @Valid DepartmentDTO department);

    @PutMapping("/{id}")
    ResponseEntity<Department> editDepartment(@RequestBody @Valid DepartmentDTO department, @PathVariable long id);
}
