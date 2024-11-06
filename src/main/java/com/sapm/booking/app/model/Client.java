package com.sapm.booking.app.model;

import lombok.Data;

import jakarta.persistence.*;


@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    private String clientCode;

    @OneToOne
    @MapsId
    @JoinColumn(name = "person_id")
    private Person person;
}
