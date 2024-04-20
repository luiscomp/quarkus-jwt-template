package com.logicsoftware.repositories;

import com.logicsoftware.models.Usuario;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UsuarioRepository extends BaseRepository<Usuario> {
    
    public Optional<Usuario> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
