package com.proyecto.time.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.time.entities.UsersEntities;
import com.proyecto.time.entities.Usuario;
import com.proyecto.time.records.UsersRequest;
import com.proyecto.time.respository.UserRepository;

@Service
public class UsersServices {

    @Autowired
    private UserRepository timeTrackingRepository;
    public UsersEntities saveTimeTracking(UsersRequest request, Usuario user) {
        System.out.println(user);
        var time = UsersEntities.builder()
            .username(request.username())
            .email(request.email())
            .password(request.password())
            .createdAt(request.createdAt())
            .updatedAt(request.updatedAt())
            .usuario(user)
            .build();
        timeTrackingRepository.save(time);
        return time;
    }

    public List<UsersEntities> obtenerRegistrosPorUsuario(Usuario usuario) {
        return timeTrackingRepository.findByUsuario(usuario);
    }
}
