package com.petproject.itmoacsbackend.entities;

import com.petproject.itmoacsbackend.enums.GlobalRole;
import com.petproject.itmoacsbackend.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "_user_")
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GlobalRole globalRole;

    private Boolean isRenter = true;

    private Boolean isLandlord = false;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String patronymic;

    @OneToMany(mappedBy = "userId",  cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<ContactEntity> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<PropertyEntity> properties = new ArrayList<>();

    @OneToMany(mappedBy = "id",  cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<ReviewEntity> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "senderId", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<MessageEntity> messages = new ArrayList<>();

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
