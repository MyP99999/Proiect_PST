package com.FCSB.FCSB.repositories;

import com.FCSB.FCSB.entities.Clocking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClockingRepository extends JpaRepository<Clocking, Integer> {

}
