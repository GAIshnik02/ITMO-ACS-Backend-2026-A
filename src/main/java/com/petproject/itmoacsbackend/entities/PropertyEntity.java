package com.petproject.itmoacsbackend.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "property")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userId;

    @OneToMany(mappedBy = "propertyId",  cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<PropertyImageEntity> propertyImages;

    @OneToMany(mappedBy = "propertyId",  cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<ReviewEntity> reviews = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private Float square;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String postalCode;

    private String nearestSubway;

    private Double latitude;

    private Double longitude;

    private Boolean available = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
