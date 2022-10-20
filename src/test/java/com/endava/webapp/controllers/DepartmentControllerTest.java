package com.endava.webapp.controllers;

import com.endava.webapp.controllers.department.DepartmentController;
import com.endava.webapp.controllers.department.DepartmentControllerImpl;
import com.endava.webapp.domain.service.department.DepartmentService;
import com.endava.webapp.domain.service.department.DepartmentNotFoundException;
import com.endava.webapp.domain.dto.DepartmentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ValidationException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentControllerImpl.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private DepartmentController departmentController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final DepartmentDTO validDepartment = new DepartmentDTO("Java", "Chisinau");

    private final DepartmentDTO emptyFieldDepartment = new DepartmentDTO("Java", "");

    private final DepartmentDTO blankFieldDepartment = new DepartmentDTO("Java", "   ");

    private final DepartmentDTO nullFieldDepartment = new DepartmentDTO("Java", null);

    @Test
    void getDepartmentByIdWhenIdIsNotContained() throws Exception {
        when(departmentService.getById(1L)).thenThrow(DepartmentNotFoundException.class);

        mockMvc.perform(get("/departments/1"))
                .andExpect(status().is(404));
    }

    @Test
    void getDepartmentByIdWhenIdIsContained() throws Exception {

        when(departmentService.getById(1L)).thenReturn(validDepartment.toDepartment());

        mockMvc.perform(get("/departments/1"))
                .andExpect(status().is(302));
    }

    @Test
    void addDepartmentWhenFieldIsBlank() throws Exception {
        String departmentJson = objectMapper.writeValueAsString(blankFieldDepartment);

        when(departmentService.saveDepartment(blankFieldDepartment.toDepartment())).thenThrow(ValidationException.class);

        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson))
                .andExpect(status().is(400));
    }

    @Test
    void addDepartmentWhenFieldIsEmpty() throws Exception {

        String departmentJson = objectMapper.writeValueAsString(emptyFieldDepartment);

        when(departmentService.saveDepartment(emptyFieldDepartment.toDepartment())).thenThrow(ValidationException.class);

        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson))
                .andExpect(status().is(400));

    }

    @Test
    void addDepartmentWhenFieldIsNull() throws Exception {

        String departmentJson = objectMapper.writeValueAsString(nullFieldDepartment);

        when(departmentService.saveDepartment(nullFieldDepartment.toDepartment())).thenThrow(ValidationException.class);

        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson))
                .andExpect(status().is(400));
    }

    @Test
    void addDepartmentWhenRequestBodyIsValid() throws Exception {

        String departmentJson = objectMapper.writeValueAsString(validDepartment);

        when(departmentService.saveDepartment(validDepartment.toDepartment())).thenReturn(validDepartment.toDepartment());

        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson))
                .andExpect(status().is(201));

    }

    @Test
    void editDepartmentWhenFieldIsBlank() throws Exception {

        String departmentJson = objectMapper.writeValueAsString(blankFieldDepartment);

        mockMvc.perform(put("/departments/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson))
                .andExpect(status().is(400));
    }

    @Test
    void editDepartmentWhenFieldIsEmpty() throws Exception {

        String departmentJson = objectMapper.writeValueAsString(emptyFieldDepartment);

        mockMvc.perform(put("/departments/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson))
                .andExpect(status().is(400));
    }

    @Test
    void editDepartmentWhenFieldIsNull() throws Exception {

        String departmentJson = objectMapper.writeValueAsString(nullFieldDepartment);

        mockMvc.perform(put("/departments/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson))
                .andExpect(status().is(400));
    }

    @Test
    void editDepartmentWhenIdIsNotContained() throws Exception {

        String departmentJson = objectMapper.writeValueAsString(validDepartment);

        when(departmentService.updateDepartment(1L, validDepartment.toDepartment())).
                thenThrow(DepartmentNotFoundException.class);

        mockMvc.perform(put("/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson))
                .andExpect(status().is(404));

    }
}