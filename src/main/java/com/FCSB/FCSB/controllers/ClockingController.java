package com.FCSB.FCSB.controllers;

import com.FCSB.FCSB.entities.Clocking;
import com.FCSB.FCSB.services.ClockingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/clocking")
public class ClockingController {

    private final ClockingService clockingService;

    @Autowired
    public ClockingController(ClockingService clockingService) {
        this.clockingService = clockingService;
    }

    @GetMapping
    public List<Clocking> getAllClocking() {
        return clockingService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clocking> getClockingById(@PathVariable Integer id) {
        Clocking clocking = clockingService.findById(id);
        return ResponseEntity.ok(clocking);
    }

    @PostMapping
    public ResponseEntity<Clocking> createClocking(@RequestBody Clocking clocking) {
        Clocking savedClocking = clockingService.save(clocking);
        return ResponseEntity.ok(savedClocking);
    }

    @PatchMapping("/{id}/end")
    public ResponseEntity<Clocking> updateClockingEndTime(@PathVariable Integer id, @RequestBody Time newEndTime) {
        Clocking updatedClocking = clockingService.updateEndTime(id, newEndTime);
        return ResponseEntity.ok(updatedClocking);
    }

}
