package com.edstem.employeeManagementSystem.repository;

import com.edstem.employeeManagementSystem.model.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Repository
public interface EmployeeRepository extends  JpaRepository<Employee,Long>  {
    boolean existsByEmail(String email);
    List<Employee> findByDepartment(String department);
}
