package com.FCSB.FCSB.services;

import com.FCSB.FCSB.entities.Clocking;
import com.FCSB.FCSB.repositories.ClockingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
public class ClockingService {

    private final ClockingRepository clockingRepository;

    @Autowired
    public ClockingService(ClockingRepository clockingRepository) {
        this.clockingRepository = clockingRepository;
    }

    public List<Clocking> findAll() {
        return clockingRepository.findAll();
    }

    public Clocking findById(Integer id) {
        return clockingRepository.findById(id).orElseThrow(() -> new RuntimeException("Clocking not found"));
    }

    public Clocking save(Clocking clocking) {
        return clockingRepository.save(clocking);
    }


    public Clocking updateEndTime(Integer id, Time newEndTime) {
        Clocking clocking = findById(id); // or handle null
        System.out.println(clocking);
        clocking.setEnd(newEndTime);
        return clockingRepository.save(clocking);
    }


}
