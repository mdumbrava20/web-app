package com.endava.webapp.service;

import com.endava.webapp.domain.repository.DepartmentRepository;
import com.endava.webapp.domain.service.department.DepartmentService;
import com.endava.webapp.domain.service.department.DepartmentNotFoundException;
import com.endava.webapp.domain.service.department.DepartmentServiceImpl;
import com.endava.webapp.domain.models.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class DepartmentServiceTest {

    private final DepartmentRepository departmentRepository = mock(DepartmentRepository.class);

    private DepartmentService departmentService;

    private final Department validDepartment = new Department(1L, "Java", "Chisinau");

    private final Department nullFieldDepartment = new Department(1L, "Java", null);

    private final Department emptyFieldDepartment = new Department(1L, "Java", "");

    private final Department blankFieldDepartment = new Department(1L, "Java", "  ");

    @BeforeEach
    void setUp() {
        departmentService = new DepartmentServiceImpl(departmentRepository);
    }

    @Test
    void getListDepartmentWhenNoDataStored() {

        when(departmentRepository.findAll()).thenReturn(Collections.emptyList());

        List<Department> result = departmentService.getListDepartment();

        assertEquals(Collections.emptyList(), result);

    }

    @Test
    void getListDepartmentWithDataStored() {
        List<Department> departmentList = Arrays.asList(validDepartment,
                new Department(2L, ".Net", "London"),
                new Department(3L, "C#", "NewYork"));

        when(departmentRepository.findAll()).thenReturn(departmentList);

        List<Department> result = departmentService.getListDepartment();

        assertEquals(departmentList, result);
    }

    @Test
    void getByIdWhenIdIsNotContained() {

        when(departmentRepository.findById(1L)).thenThrow(DepartmentNotFoundException.class);

        assertThrows(DepartmentNotFoundException.class, () -> departmentService.getById(1L));
    }

    @Test
    void getByIdWhenIdIsInvalid() {

        when(departmentRepository.findById(-1L)).thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(HttpClientErrorException.NotFound.class, () -> departmentService.getById(-1L));
    }

    @Test
    void getByIdWhenIdIsContained() {

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(validDepartment));

        Department result = departmentService.getById(1L);

        assertEquals(validDepartment, result);
    }

    @Test
    void saveDepartmentWhenFieldIsBlank() {

        when(departmentRepository.save(blankFieldDepartment)).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> departmentService.saveDepartment(blankFieldDepartment));
    }

    @Test
    void saveDepartmentWhenFieldIsEmpty() {

        when(departmentRepository.save(emptyFieldDepartment)).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> departmentService.saveDepartment(emptyFieldDepartment));
    }

    @Test
    void saveDepartmentWhenFieldIsNull() {

        when(departmentRepository.save(nullFieldDepartment)).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> departmentService.saveDepartment(nullFieldDepartment));
    }

    @Test
    void saveDepartmentWhenParameterIsValid() {

        when(departmentRepository.save(validDepartment)).thenReturn(validDepartment);

        Department result = departmentService.saveDepartment(validDepartment);

        assertEquals(validDepartment, result);
    }

    @Test
    void updateDepartmentWhenFieldIsNull() {

        when(departmentRepository.save(any())).thenThrow(ConstraintViolationException.class);
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(validDepartment));

        assertThrows(ConstraintViolationException.class,
                () -> departmentService.updateDepartment(1L, nullFieldDepartment));
    }

    @Test
    void updateDepartmentWhenFieldIsEmpty() {

        when(departmentRepository.save(any())).thenThrow(ConstraintViolationException.class);
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(validDepartment));

        assertThrows(ConstraintViolationException.class,
                () -> departmentService.updateDepartment(1L, emptyFieldDepartment));
    }

    @Test
    void updateDepartmentWhenFieldIsBlank() {

        when(departmentRepository.save(any())).thenThrow(ConstraintViolationException.class);
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(validDepartment));

        assertThrows(ConstraintViolationException.class,
                () -> departmentService.updateDepartment(1L, blankFieldDepartment));
    }

    @Test
    void updateDepartmentWhenParameterIsValid() {
        Department newDepartment = new Department(1L, "Kotlin", "Budapest");

        when(departmentRepository.save(validDepartment)).thenReturn(newDepartment);
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(validDepartment));

        Department result = departmentService.updateDepartment(1L, newDepartment);

        assertEquals(newDepartment, result);
    }
}