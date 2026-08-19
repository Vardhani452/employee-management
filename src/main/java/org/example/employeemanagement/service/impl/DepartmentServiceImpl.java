package org.example.employeemanagement.service.impl;

import org.example.employeemanagement.dto.DepartmentRequest;
import org.example.employeemanagement.dto.DepartmentResponse;
import org.example.employeemanagement.entity.Department;
import org.example.employeemanagement.repository.DepartmentRepository;
import org.example.employeemanagement.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentResponse saveDepartment(DepartmentRequest request) {

        Department department = new Department();
        department.setName(request.getName());

        Department savedDepartment = departmentRepository.save(department);

        DepartmentResponse response = new DepartmentResponse();
        response.setId(savedDepartment.getId());
        response.setName(savedDepartment.getName());

        return response;
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(department -> {
                    DepartmentResponse response = new DepartmentResponse();
                    response.setId(department.getId());
                    response.setName(department.getName());
                    return response;
                })
                .toList();
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setName(department.getName());

        return response;
    }
}