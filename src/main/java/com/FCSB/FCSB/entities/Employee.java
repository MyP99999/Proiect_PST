package com.FCSB.FCSB.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "employee")
@Data
// Generates all the boilerplate that is normally associated with simple POJOs (Plain Old Java Objects) such as getters, setters, equals, hashCode and toString methods, and more.
@NoArgsConstructor // Will generate a constructor with no parameters.
@AllArgsConstructor // Generates a constructor with 1 parameter for each field in your class.
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @OneToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
