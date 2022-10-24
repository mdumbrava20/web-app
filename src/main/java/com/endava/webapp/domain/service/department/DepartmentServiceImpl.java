package com.endava.webapp.domain.service.department;

import com.endava.webapp.domain.models.Department;
import com.endava.webapp.domain.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getListDepartment() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getById(long id) {
        return departmentRepository.findById(id).
                orElseThrow(() -> new DepartmentNotFoundException("Department with id: " + id + " not found"));
    }

    @Override
    @Transactional
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    @Transactional
    public Department updateDepartment(long id, Department department) {
        Department oldDepartment = getById(id);
        oldDepartment.setName(department.getName());
        oldDepartment.setLocation(department.getLocation());
        return departmentRepository.save(oldDepartment);
    }
}
