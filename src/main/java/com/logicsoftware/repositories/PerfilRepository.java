package com.logicsoftware.repositories;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

import com.logicsoftware.dtos.perfil.FiltroPerfilDTO;
import com.logicsoftware.models.Perfil;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class PerfilRepository implements PanacheRepository<Perfil> {

    public PanacheQuery<Perfil> aplicarFiltros(FiltroPerfilDTO filtro) {
        if(Objects.isNull(filtro)) return findAll();

        PanacheQuery<Perfil> query = findAll();

        if(Objects.nonNull(filtro.getNome())) {
            query = query.filter("nome", Parameters.with("nome", filtro.getNome()));
        }

        return query;
    }

    public List<Perfil> findPage(FiltroPerfilDTO filter, Integer page, Integer size) {
        PanacheQuery<Perfil> query = aplicarFiltros(filter);
        query.page(Page.of(page - 1, size));
        return query.list();
    }

    public Long count(FiltroPerfilDTO filter) {
        PanacheQuery<Perfil> query = aplicarFiltros(filter);
        return query.count();
    }

    public Perfil create(Perfil usuario) {
        persist(usuario);
        return usuario;
    }

    public Perfil update(Perfil usuario) {
        persist(usuario);
        return usuario;
    }

    public boolean delete(Long id) {
        return deleteById(id);
    }
}
