package com.logicsoftware.repositories;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import com.logicsoftware.models.Usuario;

@ApplicationScoped
public class UsuarioRepository extends BaseRepository<Usuario> {
    
    public Optional<Usuario> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
