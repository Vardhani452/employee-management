package org.example.employeemanagement.service.impl;

import org.example.employeemanagement.dto.EmployeeRequest;
import org.example.employeemanagement.dto.EmployeeResponse;
import org.example.employeemanagement.entity.Department;
import org.example.employeemanagement.entity.Employee;
import org.example.employeemanagement.exception.EmployeeNotFoundException;
import org.example.employeemanagement.mapper.EmployeeMapper;
import org.example.employeemanagement.repository.DepartmentRepository;
import org.example.employeemanagement.repository.EmployeeRepository;
import org.example.employeemanagement.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository){
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }




    @Override
    public EmployeeResponse saveEmployee(EmployeeRequest employee) {
        Department department = departmentRepository.findById(employee.getDepartmentId()).orElseThrow(()->new EmployeeNotFoundException("Department not found with id: " + employee.getDepartmentId()));
        Employee emp = EmployeeMapper.toEntity(employee);
        emp.setDepartment(department);
        Employee saveEmployee = employeeRepository.save(emp);

        EmployeeResponse empResponse = EmployeeMapper.toResponse(saveEmployee);
        return empResponse;
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        List<EmployeeResponse> responses = new ArrayList<>();

        for(Employee employee : employees){
            EmployeeResponse response = EmployeeMapper.toResponse(employee);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        Employee emp = employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee not found with id: " + id));
        return EmployeeMapper.toResponse(emp);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        Department department = departmentRepository.findById(employee.getDepartmentId()).orElseThrow(()->new EmployeeNotFoundException("Department not found with id: " + employee.getDepartmentId()));
        if (existingEmployee != null) {
            existingEmployee.setName(employee.getName());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setSalary(employee.getSalary());
            existingEmployee.setDepartment(department);
            Employee response =  employeeRepository.save(existingEmployee);
            return EmployeeMapper.toResponse(response);
        }
        return null;
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Optional<EmployeeResponse> findByEmail(String email) {
        return employeeRepository.findByEmail(email).map(EmployeeMapper::toResponse);
    }

    @Override
    public List<EmployeeResponse> findByName(String name) {
        return employeeRepository.findByName(name).stream().map(EmployeeMapper::toResponse).toList();
    }

    @Override
    public List<EmployeeResponse> findBySalaryGreaterThan(Double salary) {
        return employeeRepository.findBySalaryGreaterThan(salary).stream().map(EmployeeMapper::toResponse).toList();
    }

    @Override
    public List<EmployeeResponse> searchByName(String keyword) {
        return employeeRepository.findByNameContaining(keyword).stream().map(EmployeeMapper::toResponse).toList();
    }

    public List<EmployeeResponse> getEmployeeBySalary(Double salary){
        return employeeRepository.getEmployeeBySalary(salary).stream().map(EmployeeMapper::toResponse).toList();
    }

    public int updateSalary(Long id, Double salary){
        return employeeRepository.updateSalary(id,salary);
    }

    @Override
    public Page<EmployeeResponse> getEmployees(int page, int size){
        Pageable pageable = PageRequest.of(page,size);

        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        return employeePage.map(EmployeeMapper::toResponse);
    }
}
