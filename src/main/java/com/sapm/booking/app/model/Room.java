package com.sapm.booking.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

@Entity
@Table(name = "rooms", indexes = {
        @Index(name = "idx_room_id", columnList = "room_id"),
        @Index(name = "idx_room_number", columnList = "number")
})
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Long id;

    @NotBlank(message = "Number is required")
    private String number;

    @NotNull(message = "Floor is required")
    private Integer floor;
    private String description;
    private String characteristics;
    @NotNull(message = "Daily price is required")
    @Column(name = "daily_price")
    private Double dailyPrice;
    @Column(name = "room_type")
    private String roomType;
    private String status;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> reservation;
}

