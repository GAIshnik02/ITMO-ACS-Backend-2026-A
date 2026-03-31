package com.petproject.itmoacsbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "chat")
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity user1Id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity user2Id;

    @OneToMany(mappedBy = "chatId", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<MessageEntity> messages = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
