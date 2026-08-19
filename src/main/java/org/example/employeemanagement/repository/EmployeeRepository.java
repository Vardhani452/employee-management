package org.example.employeemanagement.repository;

import org.example.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    List<Employee> findByName(String name);

    List<Employee> findBySalaryGreaterThan(Double salary);

    List<Employee> findByNameContaining(String keyword);

    List<Employee> findByOrderBySalaryDesc();

    boolean existsByEmail(String email);

    long countBySalaryGreaterThan(Double salary);
    @Query("select e from Employee e where e.salary > :salary")
    List<Employee> getEmployeeBySalary(@Param("salary") Double salary);

    @Modifying
    @Transactional
    @Query("Update Employee e set e.salary= :salary where e.id = :id ")
    int updateSalary(@Param("id") long id, @Param("salary") Double salary);

}
