package com.proyecto.time.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UsersEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false)
    private String username;

    private String email;

    private String password;

    private String createdAt;

    private String updatedAt;

    @ManyToOne
    @JoinColumn(name = "employe_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    // Campo calculado
    @JsonProperty("employe_Id")
    public Long getEmployeId() {
        return usuario != null ? usuario.getId() : null;
    }
}