package com.endava.webapp.controllers.employee;

import com.endava.webapp.domain.dto.EmployeeDTO;
import com.endava.webapp.domain.models.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/employees")
public interface EmployeeController {

    @GetMapping()
    ResponseEntity<List<Employee>> getAllEmployees();

    @GetMapping("/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable() long id);

    @PostMapping()
    ResponseEntity<Employee> addEmployee(@RequestBody @Valid EmployeeDTO employee);

    @PutMapping("/{id}")
    ResponseEntity<Employee> editEmployee(@PathVariable long id, @RequestBody @Valid EmployeeDTO employee);
}
