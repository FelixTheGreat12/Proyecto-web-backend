package com.proyecto.time.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.time.entities.UsersEntities;
import com.proyecto.time.entities.Usuario;
import com.proyecto.time.records.UsersRequest;
import com.proyecto.time.respository.UserRepository;
import com.proyecto.time.respository.UsuarioRepository;
import com.proyecto.time.service.UsersServices;

@RestController
@RequestMapping("/api/v1/users")
public class UsersControllers {

    private final UsersServices userServices;
    private final UserRepository usersRepository;
    private final UsuarioRepository usuarioRepository;

    public UsersControllers(UserRepository userRepository, UsuarioRepository usuarioRepository,
            UsersServices userService) {
        this.usersRepository = userRepository;
        this.usuarioRepository = usuarioRepository;
        this.userServices = userService;
    }

    @GetMapping
    public List<UsersEntities> obtenerTodos() {
        return usersRepository.findAll();
    }

    @PostMapping("/{usuarioId}")
    public UsersEntities registerUser(@PathVariable Long usuarioId, @RequestBody UsersRequest timeTracking) {
        Usuario user = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                System.out.println(user);
        return userServices.saveTimeTracking(timeTracking, user);
    }

    // Obtener un registro específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<UsersEntities> obtenerPorId(@PathVariable Long id) {
        return usersRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar un registro existente
    @PutMapping("/{id}")
    public ResponseEntity<UsersEntities> actualizarUser(@PathVariable Long id,
            @RequestBody UsersEntities detallesActualizados) {
        return usersRepository.findById(id).map(Users -> {
            Users.setUsername(detallesActualizados.getUsername());
            Users.setEmail(detallesActualizados.getEmail());
            Users.setPassword(detallesActualizados.getPassword());
            Users.setCreatedAt(detallesActualizados.getCreatedAt());
            Users.setUpdatedAt(detallesActualizados.getUpdatedAt());
            UsersEntities actualizado = usersRepository.save(Users);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un registro por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarUser(@PathVariable Long id) {
        return usersRepository.findById(id).map(users -> {
            usersRepository.delete(users);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // Obtener todos los registros de time tracking de un usuario específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Map<String, Object>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return usuarioRepository.findById(usuarioId).map(usuario -> {
            List<UsersEntities> registros = userServices.obtenerRegistrosPorUsuario(usuario);
            Map<String, Object> response = new HashMap<>();
            response.put("times", registros);
            return ResponseEntity.ok(response);
        }).orElse(ResponseEntity.notFound().build());
    }
}
