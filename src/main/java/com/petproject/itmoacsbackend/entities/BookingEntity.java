package com.petproject.itmoacsbackend.entities;


import aQute.bnd.annotation.licenses.CPL_1_0;
import com.petproject.itmoacsbackend.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "booking")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private UserEntity renterId;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private PropertyEntity propertyId;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private PaymentEntity paymentId;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus bookingStatus =  BookingStatus.PENDING;

    @Column(nullable = false)
    private Double totalPrice;

    private Integer guestsCount;

    private String details;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
