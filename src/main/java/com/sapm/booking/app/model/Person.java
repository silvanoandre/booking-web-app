package com.sapm.booking.app.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "persons")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String middleName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    private String documentNumber;
    private String address;
    private String phone;
    private String email;
    private String login;
    private String password;
    private String state;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Employee employee;

}

