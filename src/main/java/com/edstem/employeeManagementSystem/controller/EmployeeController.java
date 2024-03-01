package com.edstem.employeeManagementSystem.controller;

import com.edstem.employeeManagementSystem.contract.EmployeReq;
import com.edstem.employeeManagementSystem.contract.EmployeRes;
import com.edstem.employeeManagementSystem.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Component
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @PostMapping
    public EmployeRes addEmployee(@Valid @RequestBody EmployeReq employeReq){
        return employeeService.addEmployee(employeReq);
    }
    @GetMapping("/{id}")
    public EmployeRes getEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id);
    }
    @GetMapping("/department/{department}")
    public List<EmployeRes> getEmployeeByDepartment(@RequestParam String department){
        return employeeService.getByDepartment(department);
    }
}
