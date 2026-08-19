package org.example.employeemanagement.mapper;

import org.example.employeemanagement.dto.EmployeeRequest;
import org.example.employeemanagement.dto.EmployeeResponse;
import org.example.employeemanagement.entity.Employee;

public class EmployeeMapper {

    public static Employee toEntity(EmployeeRequest request){
        Employee emp = new Employee();
        emp.setName(request.getName());
        emp.setEmail(request.getEmail());
        emp.setSalary(request.getSalary());
        return emp;
    }

    public static EmployeeResponse toResponse(Employee employee){
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setEmail(employee.getEmail());
        response.setSalary(employee.getSalary());
        return response;
    }
}
