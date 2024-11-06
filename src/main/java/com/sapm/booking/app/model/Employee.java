package com.sapm.booking.app.model;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "person_id")
    private Person person;


    private BigDecimal salary;
    private String access;


}

