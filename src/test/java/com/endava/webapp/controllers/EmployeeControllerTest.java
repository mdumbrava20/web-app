package com.endava.webapp.controllers;

import com.endava.webapp.controllers.employee.EmployeeController;
import com.endava.webapp.controllers.employee.EmployeeControllerImpl;
import com.endava.webapp.domain.service.department.DepartmentService;
import com.endava.webapp.domain.service.employee.EmployeeService;
import com.endava.webapp.domain.service.employee.EmployeeNotFoundException;
import com.endava.webapp.domain.models.Department;
import com.endava.webapp.domain.models.Employee;
import com.endava.webapp.domain.dto.EmployeeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeControllerImpl.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private EmployeeController employeeController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllEmployees() {
    }

    @Test
    void getEmployeeByIdWhenIdIsNotContained() throws Exception {
        when(employeeService.getEmployeeById(1L)).thenThrow(EmployeeNotFoundException.class);

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().is(404));

    }

    @Test
    void getEmployeeByIdWhenIdContained() throws Exception {

        Department department = createValidDepartment();
        EmployeeDTO employee = createValidEmployeeDTO();

        when(employeeService.getEmployeeById(1L)).thenReturn(employee.toEmployee(department));

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().is(302));

    }

    @Test
    void addEmployeeWhenFieldIsNull() throws Exception {
        Department department = createValidDepartment();
        EmployeeDTO employee = createValidEmployeeDTO();
        employee.setFirstName(null);

        when(departmentService.getById(1L)).thenReturn(department);
        when(employeeService.saveEmployee(employee.toEmployee(department))).thenThrow(RuntimeException.class);

        String employeeJson = objectMapper.writeValueAsString(employee);
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .content(employeeJson))
                .andExpect(status().is(400));

    }

    @Test
    void addEmployeeWhenFieldIsEmpty() throws Exception {
        Department department = createValidDepartment();
        EmployeeDTO employee = createValidEmployeeDTO();
        employee.setFirstName("");

        when(departmentService.getById(1L)).thenReturn(department);
        when(employeeService.saveEmployee(employee.toEmployee(department))).thenThrow(RuntimeException.class);

        String employeeJson = objectMapper.writeValueAsString(employee);
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .content(employeeJson))
                .andExpect(status().is(400));

    }

    @Test
    void addEmployeeWhenFieldIsBlank() throws Exception {
        Department department = createValidDepartment();
        EmployeeDTO employee = createValidEmployeeDTO();
        employee.setFirstName("   ");

        when(departmentService.getById(1L)).thenReturn(department);
        when(employeeService.saveEmployee(employee.toEmployee(department))).thenThrow(RuntimeException.class);

        String employeeJson = objectMapper.writeValueAsString(employee);
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .content(employeeJson))
                .andExpect(status().is(400));

    }

    @Test
    void addEmployeeRequestBodyIsValid() throws Exception {
        Department department = createValidDepartment();
        EmployeeDTO employee = createValidEmployeeDTO();

        when(departmentService.getById(1L)).thenReturn(department);
        when(employeeService.saveEmployee(employee.toEmployee(department))).thenReturn(employee.toEmployee(department));

        String employeeJson = objectMapper.writeValueAsString(employee);
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .content(employeeJson))
                .andExpect(status().is(201));

    }

    @Test
    void editEmployeeWhenRequestBodyIsValid() throws Exception {
        Employee oldEmployee = createValidEmployee();

        Department newDepartment = new Department(2L, "Kotlin", "Budapest");
        EmployeeDTO newEmployeeDTO = createValidEmployeeDTO();
        newEmployeeDTO.setDepartmentId(2L);
        newEmployeeDTO.setSalary(500.0);

        Employee newEmployee = newEmployeeDTO.toEmployee(newDepartment);

        when(employeeService.getEmployeeById(1L)).thenReturn(oldEmployee);
        when(departmentService.getById(2L)).thenReturn(newDepartment);
        when(employeeService.updateEmployee(oldEmployee, newEmployee)).thenReturn(newEmployee);

        String newEmployeeJson = objectMapper.writeValueAsString(newEmployee);

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployeeJson))
                .andExpect(status().is(202));
    }

    @Test
    void editEmployeeWhenFieldIsBlank() throws Exception {
        EmployeeDTO newEmployeeDTO = createValidEmployeeDTO();
        newEmployeeDTO.setFirstName("  ");

        String newEmployeeJson = objectMapper.writeValueAsString(newEmployeeDTO);

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployeeJson))
                .andExpect(status().is(400));
    }

    @Test
    void editEmployeeWhenFieldIsNull() throws Exception {
        EmployeeDTO newEmployeeDTO = createValidEmployeeDTO();
        newEmployeeDTO.setLastName(null);

        String newEmployeeJson = objectMapper.writeValueAsString(newEmployeeDTO);

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployeeJson))
                .andExpect(status().is(400));
    }

    @Test
    void editEmployeeWhenFieldIsEmpty() throws Exception {
        EmployeeDTO newEmployeeDTO = createValidEmployeeDTO();
        newEmployeeDTO.setLastName("");

        String newEmployeeJson = objectMapper.writeValueAsString(newEmployeeDTO);

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployeeJson))
                .andExpect(status().is(400));
    }

    private EmployeeDTO createValidEmployeeDTO() {
        return new EmployeeDTO(1L, "Andrew",
                "Garfield", "andrew_garfield",
                "123456", 200.0);
    }

    private Department createValidDepartment() {
        return new Department(1L, "Java", "Chisinau");
    }

    private Employee createValidEmployee() {
        Department department = createValidDepartment();
        EmployeeDTO employeeDTO = createValidEmployeeDTO();
        return employeeDTO.toEmployee(department);
    }
}