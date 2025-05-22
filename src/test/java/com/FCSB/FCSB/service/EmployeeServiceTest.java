package com.FCSB.FCSB.service;

import com.FCSB.FCSB.entities.Employee;
import com.FCSB.FCSB.repositories.EmployeeRepository;
import com.FCSB.FCSB.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testFindEmployeeById() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Ion");
        employee.setLastName("Popescu");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.findEmployeeById(1);

        assertTrue(result.isPresent());
        assertEquals("Ion", result.get().getFirstName());
        assertEquals("Popescu", result.get().getLastName());

        verify(employeeRepository, times(1)).findById(1);
    }
}
