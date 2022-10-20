package com.endava.webapp.domain.service.department;

import com.endava.webapp.domain.models.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> getListDepartment();

    Department getById(long id);

    Department saveDepartment(Department department);

    Department updateDepartment(long id, Department department);
}
