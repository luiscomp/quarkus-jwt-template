package com.logicsoftware.services;

import com.logicsoftware.dtos.perfil.CriarPerfilDTO;
import com.logicsoftware.dtos.perfil.PerfilDTO;
import com.logicsoftware.models.Perfil;
import com.logicsoftware.repositories.PerfilRepository;
import com.logicsoftware.utils.database.Pageable;
import com.logicsoftware.utils.i18n.Messages;
import com.logicsoftware.utils.mappers.GenericMapper;
import io.quarkus.arc.log.LoggerName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.apache.commons.beanutils.BeanUtils;
import org.jboss.logging.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@ApplicationScoped
public class PerfilService {
    @LoggerName("PerfilService")
    Logger logger;

    @Inject 
    GenericMapper mapper;

    @Inject
    Messages message;

    @Inject
    PerfilRepository perfilRepository;

    private Perfil findUserById(Long id) {
        Optional<Perfil> perfilEncontrado =  perfilRepository.findByIdOptional(id);
        return perfilEncontrado.orElseThrow(() -> new NotFoundException(message.getMessage("usuario.nao.encontrado", id)));
    }

    public Pageable<Perfil> listar(PerfilDTO filtro, Integer page, Integer size) {
        return perfilRepository.findPage(filtro, page, size);
    }

    public Perfil recuperar(Long id) {
        return findUserById(id);
    }

    public Perfil criar(CriarPerfilDTO perfil) {
        Perfil novoPerfil = mapper.toObject(perfil, Perfil.class);
        perfilRepository.persist(novoPerfil);
        return novoPerfil;
    }

    public Perfil atualizar(CriarPerfilDTO perfil, Long id) throws IllegalAccessException, InvocationTargetException {
        Perfil perfilEncontrado =  findUserById(id);
        BeanUtils.copyProperties(perfilEncontrado, perfil);
        perfilRepository.persist(perfilEncontrado);
        return perfilEncontrado;
    }

    public void deletar(Long id) {
        perfilRepository.deleteById(id);
    }
}
