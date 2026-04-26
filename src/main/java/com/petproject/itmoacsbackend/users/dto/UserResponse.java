package com.petproject.itmoacsbackend.users.dto;

import com.petproject.itmoacsbackend.auth.enums.GlobalRole;
import com.petproject.itmoacsbackend.entities.PropertyEntity;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record UserResponse(

        Long id,
        GlobalRole role,

        Boolean isRenter,

        Boolean isLandlord,

        String username,

        String email,

        String phoneNumber,

        String firstName,

        String lastName,

        String patronymic

) {

}
