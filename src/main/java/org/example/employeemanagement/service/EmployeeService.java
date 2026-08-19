package org.example.employeemanagement.service;

import org.example.employeemanagement.dto.EmployeeRequest;
import org.example.employeemanagement.dto.EmployeeResponse;
import org.example.employeemanagement.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeResponse saveEmployee(EmployeeRequest employee);
    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse getEmployeeById(Long id);
    EmployeeResponse updateEmployee(Long id, EmployeeRequest employee);
    void deleteEmployee(Long id);
    Optional<EmployeeResponse> findByEmail(String email);

    List<EmployeeResponse> findByName(String name);

    List<EmployeeResponse> findBySalaryGreaterThan(Double salary);

    List<EmployeeResponse> searchByName(String keyword);

    List<EmployeeResponse> getEmployeeBySalary(Double salary);

    int updateSalary(Long id, Double salary);

    Page<EmployeeResponse> getEmployees(int page, int size);
}
