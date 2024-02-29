package com.edstem.employeeManagementSystem.controller;

import com.edstem.employeeManagementSystem.contract.EmployeReq;
import com.edstem.employeeManagementSystem.contract.EmployeRes;
import com.edstem.employeeManagementSystem.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;

    @Test
    void testAddEmployee() throws Exception {
        EmployeReq request = new EmployeReq("a", "a@gmail.com", "cs");
        EmployeRes expectedResponse =
                new EmployeRes(1L, "a", "a@gmail.com", "cs");
        when(employeeService.addEmployee(any(EmployeReq.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/employee")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void testViewEmployeeById() throws Exception {
        Long id = 1L;
        EmployeRes expectedResponse =
                new EmployeRes(1L, "a", "a@gmail.com", "dev");
        when(employeeService.getEmployeeById(id)).thenReturn(expectedResponse);

        mockMvc.perform(get("/employee/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void testGetEmployeeByDepartment() throws Exception {
        String department = "dev";
        List<EmployeRes> employeRes = new ArrayList<>();
        employeRes.add(new EmployeRes(1L, "a", "a@gmail.com", department));
        when(employeeService.getByDepartment(department)).thenReturn(employeRes);
        mockMvc.perform(get("/employee/department/" + department))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(employeRes)));
    }
}
