package com.proyecto.time.records;

import java.time.LocalDateTime;

import com.proyecto.time.entities.Usuario;

public record UsersRequest(
    String username,
    String email,
    String password,
    String createdAt,
    String updatedAt
) {

}
