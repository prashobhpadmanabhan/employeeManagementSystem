package com.edstem.employeeManagementSystem.service;

import com.edstem.employeeManagementSystem.contract.EmployeReq;
import com.edstem.employeeManagementSystem.contract.EmployeRes;
import com.edstem.employeeManagementSystem.model.Employee;
import com.edstem.employeeManagementSystem.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    public EmployeRes addEmployee(EmployeReq employeReq){
        Employee employee = modelMapper.map(employeReq, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeRes.class);

    }
    public EmployeRes getEmployeeById(long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Id not found " + id));
        return modelMapper.map(employee,EmployeRes.class);
    }
    public List<EmployeRes> getByDepartment(String department){

        List<Employee>employees = (List<Employee>) employeeRepository.findByDepartment(department);
        if(employees.isEmpty()){
            throw new EntityNotFoundException("Department not found");
        }
        return employees.stream().map(item->modelMapper.map(item,EmployeRes.class)).collect(Collectors.toList());
    }
}
