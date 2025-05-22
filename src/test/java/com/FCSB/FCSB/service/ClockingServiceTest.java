package com.FCSB.FCSB.services;

import com.FCSB.FCSB.entities.Clocking;
import com.FCSB.FCSB.entities.Employee;
import com.FCSB.FCSB.repositories.ClockingRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClockingServiceTest {

    @Mock
    private ClockingRepository clockingRepository;

    @InjectMocks
    private ClockingService clockingService;

    @Test
    void testUpdateEndTime() {
        Integer clockingId = 1;
        Time newEndTime = Time.valueOf("17:00:00");

        Clocking existingClocking = new Clocking();
        existingClocking.setId(clockingId);
        existingClocking.setDate(LocalDate.of(2024, 5, 22));
        existingClocking.setStart(Time.valueOf("09:00:00"));
        existingClocking.setEnd(null); // inițial nu avea final
        existingClocking.setEmployee(new Employee()); // mock basic

        when(clockingRepository.findById(clockingId)).thenReturn(Optional.of(existingClocking));
        when(clockingRepository.save(any(Clocking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Clocking result = clockingService.updateEndTime(clockingId, newEndTime);

        assertNotNull(result);
        assertEquals(newEndTime, result.getEnd());

        verify(clockingRepository, times(1)).findById(clockingId);
        verify(clockingRepository, times(1)).save(existingClocking);
    }
}
