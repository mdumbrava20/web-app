package com.endava.webapp.domain.service.employee;

import com.endava.webapp.domain.repository.EmployeeRepository;
import com.endava.webapp.domain.models.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployeeList() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id: " + id + " not found"));
    }

    @Override
    @Transactional
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee oldEmployee, Employee employee) {
        oldEmployee.setFirstName(employee.getFirstName());
        oldEmployee.setLastName(employee.getLastName());
        oldEmployee.setEmail(employee.getEmail());
        oldEmployee.setSalary(employee.getSalary());
        oldEmployee.setPhoneNumber(employee.getPhoneNumber());
        oldEmployee.setDepartment(employee.getDepartment());
        return saveEmployee(oldEmployee);
    }

}
