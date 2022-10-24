package com.endava.webapp.service;

import com.endava.webapp.domain.repository.EmployeeRepository;
import com.endava.webapp.domain.service.employee.EmployeeService;
import com.endava.webapp.domain.service.employee.EmployeeNotFoundException;
import com.endava.webapp.domain.service.employee.EmployeeServiceImpl;
import com.endava.webapp.domain.models.Department;
import com.endava.webapp.domain.models.Employee;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    private final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    void getEmployeeListWhenNoDataStored() {

        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), employeeService.getEmployeeList());
    }

    @Test
    void getEmployeeListWhenWithStoredData() {
        List<Employee> employeeList = Arrays.asList(new Employee(), new Employee(), new Employee());

        when(employeeRepository.findAll()).thenReturn(employeeList);

        List<Employee> result = employeeService.getEmployeeList();

        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(employeeList, result)

        );
    }

    @Test
    void getEmployeeByIdWhenIdIsNotContained() {

        when(employeeRepository.findById(1L)).thenThrow(EmployeeNotFoundException.class);

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(1L));
    }

    @Test
    void getEmployeeByIdWhenIdIsContained() {

        Employee employee = createValidEmployee();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(1L);

        assertEquals(employee, result);

    }

    @Test
    void saveEmployeeWhenFieldIsBlank() {
        Employee employee = createValidEmployee();
        employee.setFirstName("   ");

        when(employeeRepository.save(employee)).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> employeeService.saveEmployee(employee));
    }

    @Test
    void saveEmployeeWhenFieldIsEmpty() {
        Employee employee = createValidEmployee();
        employee.setFirstName("");

        when(employeeRepository.save(employee)).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> employeeService.saveEmployee(employee));
    }

    @Test
    void saveEmployeeWhenFieldIsNull() {
        Employee employee = createValidEmployee();
        employee.setFirstName(null);

        when(employeeRepository.save(employee)).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> employeeService.saveEmployee(employee));
    }

    @Test
    void saveEmployeeWhenDataIsValid() {
        Employee employee = createValidEmployee();

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.saveEmployee(employee);

        assertEquals(employee, result);
    }

    @Test
    void updateEmployeeWhenFieldIsEmpty() {

        Employee employee = createValidEmployee();
        Employee newEmployee = createValidEmployee();
        newEmployee.setFirstName("");

        when(employeeRepository.save(any())).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class,
                () -> employeeService.updateEmployee(employee, newEmployee));

    }

    @Test
    void updateEmployeeWhenFieldIsBlank() {

        Employee employee = createValidEmployee();
        Employee newEmployee = createValidEmployee();
        newEmployee.setFirstName("  ");

        when(employeeRepository.save(any())).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class,
                () -> employeeService.updateEmployee(employee, newEmployee));

    }

    @Test
    void updateEmployeeWhenFieldIsNull() {

        Employee employee = createValidEmployee();
        Employee newEmployee = createValidEmployee();
        newEmployee.setFirstName(null);

        when(employeeRepository.save(any())).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class,
                () -> employeeService.updateEmployee(employee, newEmployee));

    }

    @Test
    void updateEmployeeWhenDataIsValid() {

        Employee employee = createValidEmployee();
        Department department = new Department(2L, "Kotlin", "Budapest");
        Employee newEmployee = new Employee(1L, "Stephen", "King",
                department, "stephen_king@gmail.com", "1238888", 1500.0);

        when(employeeRepository.save(any())).thenReturn(newEmployee);

        Employee result = employeeService.updateEmployee(employee, newEmployee);

        assertEquals(employee, result);
    }

    private Employee createValidEmployee() {
        Department department = new Department(1L, "Java", "Chisinau");
        return new Employee(1L, "Andrew", "Garfield",
                department, "andrew_garfield@gmail.com", "123456", 2000.0);
    }
}