package com.petproject.itmoacsbackend.chats.repositories;

import com.petproject.itmoacsbackend.chats.entities.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    Page<MessageEntity> findByChatIdOrderByTimestampDesc(Long chatId, Pageable pageable);

    Optional<MessageEntity> findFirstByChatIdOrderByCreatedAtDesc(Long id);
}
