package com.endava.webapp.controllers.employee;

import com.endava.webapp.domain.dto.EmployeeDTO;
import com.endava.webapp.domain.models.Employee;
import com.endava.webapp.domain.service.department.DepartmentService;
import com.endava.webapp.domain.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeService employeeService;

    private final DepartmentService departmentService;

    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employeeList = employeeService.getEmployeeList();
        return ResponseEntity.status(HttpStatus.OK).body(employeeList);
    }

    public ResponseEntity<Employee> getEmployeeById(long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(employee);
    }

    public ResponseEntity<Employee> addEmployee(EmployeeDTO employee) {
        long departmentId = employee.getDepartmentId();
        Employee employeeToSave = employee.toEmployee(departmentService.getById(departmentId));
        Employee savedEmployee = employeeService.saveEmployee(employeeToSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    public ResponseEntity<Employee> editEmployee(long id, EmployeeDTO employee) {
        long departmentId = employee.getDepartmentId();
        Employee oldEmployee = employeeService.getEmployeeById(id);
        Employee newEmployee = employee.toEmployee(departmentService.getById(departmentId));
        Employee updatedEmployee = employeeService.updateEmployee(oldEmployee, newEmployee);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedEmployee);
    }

}
