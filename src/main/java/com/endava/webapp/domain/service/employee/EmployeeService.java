package com.endava.webapp.domain.service.employee;

import com.endava.webapp.domain.models.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getEmployeeList();

    Employee getEmployeeById(long id);

    Employee saveEmployee(Employee employee);

    Employee updateEmployee(Employee oldEmployee, Employee employee);
}
