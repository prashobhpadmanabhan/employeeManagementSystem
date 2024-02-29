package com.edstem.employeeManagementSystem.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeRes {
    private Long id;
    private String name;
    private String email;
    private String department;
}
