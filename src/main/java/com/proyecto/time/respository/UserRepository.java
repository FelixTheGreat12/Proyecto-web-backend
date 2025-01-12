package com.proyecto.time.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.time.entities.UsersEntities;
import com.proyecto.time.entities.Usuario;

public interface UserRepository extends JpaRepository<UsersEntities, Long>{

    List<UsersEntities> findByUsuario(Usuario usuario);
}
