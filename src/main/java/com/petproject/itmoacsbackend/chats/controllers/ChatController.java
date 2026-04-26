package com.petproject.itmoacsbackend.chats.controllers;


import com.petproject.itmoacsbackend.chats.dto.ChatCreateRequest;
import com.petproject.itmoacsbackend.chats.dto.ChatResponse;
import com.petproject.itmoacsbackend.chats.dto.MessageResponse;
import com.petproject.itmoacsbackend.chats.service.ChatService;
import com.petproject.itmoacsbackend.users.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/new")
    public ResponseEntity<ChatResponse> createChat(
            @Valid @RequestBody ChatCreateRequest request,
            @AuthenticationPrincipal UserEntity user
    ) {
        ChatResponse response = chatService.createChat(request, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ChatResponse>> getChats(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal UserEntity user
    ) {
        Page<ChatResponse> chats = chatService.getChats(user, page, size);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Page<MessageResponse>> getMessages()



}
