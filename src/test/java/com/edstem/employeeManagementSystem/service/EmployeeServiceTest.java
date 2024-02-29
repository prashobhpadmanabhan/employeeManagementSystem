package com.edstem.employeeManagementSystem.service;

import com.edstem.employeeManagementSystem.contract.EmployeReq;
import com.edstem.employeeManagementSystem.contract.EmployeRes;
import com.edstem.employeeManagementSystem.model.Employee;
import com.edstem.employeeManagementSystem.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {EmployeeService.class})
@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @MockBean
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void testAddEmployee(){
        EmployeReq request=new EmployeReq("a","a@gmail.com","dev");
        Employee employee=modelMapper.map(request,Employee.class);
        EmployeRes expectedResponse=modelMapper.map(employee,EmployeRes.class);
    }

    @Test
    void testGetEmployeeById() {
        Long id = 1L;
        Employee employee = new Employee(id, "a", "a@gmail.com", "dev");
        EmployeRes expectedResponse = modelMapper.map(employee, EmployeRes.class);
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeById(id));
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        EmployeRes actualResponse = employeeService.getEmployeeById(id);
        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    void testGetByDepartment() {
        String department = "dev";
        Employee employeeOne = new Employee(1L, "a", "a@gmail.com", department);
        Employee employeeTwo = new Employee(1L, "b", "b@gmail.com", department);

        List<Employee> employees = Arrays.asList(employeeOne, employeeTwo);
        List<EmployeRes> expectedResponse =
                employees.stream()
                        .map(employee -> modelMapper.map(employee, EmployeRes.class))
                        .collect(Collectors.toList());

        when(employeeRepository.findByDepartment(department)).thenReturn(Collections.emptyList());
        assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.getByDepartment(department));
        when(employeeRepository.findByDepartment(department)).thenReturn(employees);
        List<EmployeRes> actualResponse =
                employeeService.getByDepartment(department);
        assertEquals(expectedResponse, actualResponse);
    }
}
