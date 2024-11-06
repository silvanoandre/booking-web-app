package com.sapm.booking.app.model;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "reservations")
@Data
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id", nullable = false)
    private Long voucherId;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private String voucherType;
    private String voucherNumber;
    private BigDecimal taxes;
    private BigDecimal costConsumption;
    private BigDecimal TotalCost;
    private LocalDateTime issueDate;
    private String state;
}
