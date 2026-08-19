package org.example.employeemanagement.controller;

import jakarta.validation.Valid;
import org.example.employeemanagement.dto.EmployeeRequest;
import org.example.employeemanagement.dto.EmployeeResponse;
import org.example.employeemanagement.entity.Employee;
import org.example.employeemanagement.exception.EmployeeNotFoundException;
import org.example.employeemanagement.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeResponse saveEmployee(@Valid @RequestBody EmployeeRequest employee){
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id);
    }

    @GetMapping()
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(@PathVariable Long id,
                                   @Valid @RequestBody EmployeeRequest employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "Employee deleted successfully.";
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeResponse> getByEmail(@PathVariable String email){
        EmployeeResponse response = employeeService.findByEmail(email).orElseThrow(()->new EmployeeNotFoundException("Employee not found with email: " + email));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<EmployeeResponse>> getByName(@PathVariable String name){
        return ResponseEntity.ok(employeeService.findByName(name));
    }

    @GetMapping("/salary/{salary}")
    public ResponseEntity<List<EmployeeResponse>> getBySalary(
            @PathVariable Double salary){

        return ResponseEntity.ok(
                employeeService.findBySalaryGreaterThan(salary));
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> search(
            @RequestParam String keyword){

        return ResponseEntity.ok(
                employeeService.searchByName(keyword));
    }

    @GetMapping("/salary/jpql/{salary}")
    public ResponseEntity<List<EmployeeResponse>> getEmployeeBySalary(@PathVariable Double salary){
        return ResponseEntity.ok(employeeService.getEmployeeBySalary(salary));
    }

    @PutMapping("/{id}/salary")
    public ResponseEntity<String> updateSalary(@PathVariable Long id, @RequestParam Double salary){
        employeeService.updateSalary(id,salary);
        return ResponseEntity.ok("Salary is Updated");
    }

    @GetMapping("/page")
    public ResponseEntity<Page<EmployeeResponse>> getEmployees(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "2") int size){
        return ResponseEntity.ok(employeeService.getEmployees(page,size));
    }
}
