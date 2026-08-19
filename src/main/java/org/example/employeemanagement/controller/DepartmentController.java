package org.example.employeemanagement.controller;

import org.example.employeemanagement.dto.DepartmentRequest;
import org.example.employeemanagement.dto.DepartmentResponse;
import org.example.employeemanagement.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> saveDepartment(
            @RequestBody DepartmentRequest request) {

        return ResponseEntity.ok(departmentService.saveDepartment(request));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {

        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }
}
