package com.logicsoftware.repositories;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import com.logicsoftware.dtos.usuario.FiltroUsuarioDTO;
import com.logicsoftware.models.Usuario;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public PanacheQuery<Usuario> aplicarFiltros(FiltroUsuarioDTO filtro) {
        if(Objects.isNull(filtro)) return findAll();

        PanacheQuery<Usuario> query = findAll();

        if(Objects.nonNull(filtro.getNome())) {
            query = query.filter("nome", Parameters.with("nome", filtro.getNome()));
        }

        if(Objects.nonNull(filtro.getEmail())) {
            query = query.filter("email", Parameters.with("email", filtro.getEmail()));
        }

        return query;
    }

    public List<Usuario> findPage(FiltroUsuarioDTO filter, Integer page, Integer size) {
        PanacheQuery<Usuario> query = aplicarFiltros(filter);
        query.page(Page.of(page - 1, size));
        return query.list();
    }

    public Long count(FiltroUsuarioDTO filter) {
        PanacheQuery<Usuario> query = aplicarFiltros(filter);
        return query.count();
    }
    
    public Optional<Usuario> findByEmail(String email) {
        PanacheQuery<Usuario> query = findAll();
        query.filter("email", Parameters.with("email", email));
        return query.firstResultOptional();
    }

    public Usuario create(Usuario usuario) {
        persist(usuario);
        return usuario;
    }

    public Usuario update(Usuario usuario) {
        persist(usuario);
        return usuario;
    }

    public boolean delete(Long id) {
        return deleteById(id);
    }
}
