package org.example.employeemanagement.service;

import org.example.employeemanagement.dto.DepartmentRequest;
import org.example.employeemanagement.dto.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse saveDepartment(DepartmentRequest request);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse getDepartmentById(Long id);
}
