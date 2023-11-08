package com.FCSB.FCSB.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Table(name = "clocking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clocking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_employee", nullable = false)
    private Employee employee;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "start", nullable = false)
    private Time start;

    @Column(name = "end")
    private Time end;

    @Column(name = "duration", insertable = false, updatable = false)
    private Time duration;
}
