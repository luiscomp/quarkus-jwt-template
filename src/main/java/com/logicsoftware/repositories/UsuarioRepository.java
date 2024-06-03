package com.logicsoftware.repositories;

import java.util.Optional;

import com.logicsoftware.models.Usuario;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository extends BaseRepository<Usuario, Long> {
    
    public Optional<Usuario> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
