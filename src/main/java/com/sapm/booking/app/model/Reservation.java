package com.sapm.booking.app.model;


import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    private Employee employee;

    private String reservationType;
    private LocalDateTime reservationDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private BigDecimal totalCost;
    private String observation;
    private String state;
}
