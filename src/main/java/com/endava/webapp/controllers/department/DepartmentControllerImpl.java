package com.endava.webapp.controllers.department;

import com.endava.webapp.domain.dto.DepartmentDTO;
import com.endava.webapp.domain.models.Department;
import com.endava.webapp.domain.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentControllerImpl implements DepartmentController {

    private final DepartmentService departmentService;

    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departmentList = departmentService.getListDepartment();
        return ResponseEntity.status(HttpStatus.OK).body(departmentList);
    }

    public ResponseEntity<Department> getDepartmentById(long id) {
        Department foundDepartment = departmentService.getById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(foundDepartment);
    }

    public ResponseEntity<Department> addDepartment(DepartmentDTO department) {
        Department savedDepartment = departmentService.saveDepartment(department.toDepartment());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
    }

    public ResponseEntity<Department> editDepartment(DepartmentDTO department, long id) {
        Department updatedDepartment = departmentService.updateDepartment(id, department.toDepartment());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedDepartment);
    }
}
