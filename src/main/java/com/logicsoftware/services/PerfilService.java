package com.logicsoftware.services;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import org.apache.commons.beanutils.BeanUtils;
import org.jboss.logging.Logger;

import com.logicsoftware.dtos.perfil.CriarPerfilDTO;
import com.logicsoftware.dtos.perfil.FiltroPerfilDTO;
import com.logicsoftware.dtos.perfil.PerfilDTO;
import com.logicsoftware.models.Perfil;
import com.logicsoftware.repositories.PerfilRepository;
import com.logicsoftware.utils.i18n.Messages;
import com.logicsoftware.utils.mappers.GenericMapper;

import io.quarkus.arc.log.LoggerName;

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

    public List<PerfilDTO> listar(FiltroPerfilDTO filtro, Integer page, Integer size) {
        return mapper.toList(perfilRepository.findPage(filtro, page, size), PerfilDTO.class);
    }

    public Long count(FiltroPerfilDTO filtro) {
        return perfilRepository.count(filtro);
    }

    public PerfilDTO recuperar(Long id) {
        Perfil perfilEncontrado = findUserById(id);
        return mapper.toObject(perfilEncontrado, PerfilDTO.class);
    }

    public PerfilDTO criar(CriarPerfilDTO perfil) {
        Perfil novoPerfil = mapper.toObject(perfil, Perfil.class);
        return mapper.toObject(perfilRepository.create(novoPerfil), PerfilDTO.class);
    }

    public PerfilDTO atualizar(CriarPerfilDTO perfil, Long id) throws IllegalAccessException, InvocationTargetException {
        Perfil perfilEncontrado =  findUserById(id);
        BeanUtils.copyProperties(perfilEncontrado, perfil);
        Perfil perfilAtualizado = perfilRepository.update(perfilEncontrado);
        return  mapper.toObject(perfilAtualizado, PerfilDTO.class);
    }

    public void deletar(Long id) {
        findUserById(id);
        perfilRepository.delete(id);
    }
}
