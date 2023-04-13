package com.logicsoftware.services;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import org.apache.commons.beanutils.BeanUtils;
import org.jboss.logging.Logger;

import com.logicsoftware.dtos.usuario.CriarUsuarioDTO;
import com.logicsoftware.dtos.usuario.FiltroUsuarioDTO;
import com.logicsoftware.dtos.usuario.UsuarioDTO;
import com.logicsoftware.models.Usuario;
import com.logicsoftware.repositories.UsuarioRepository;
import com.logicsoftware.utils.auth.PasswordEncoder;
import com.logicsoftware.utils.i18n.Messages;
import com.logicsoftware.utils.mappers.GenericMapper;

import io.quarkus.arc.log.LoggerName;

@ApplicationScoped
public class UsuarioService {

    @LoggerName("UsuarioService")
    Logger logger;

    @Inject 
    PasswordEncoder passwordEncoder;

    @Inject
    GenericMapper mapper;

    @Inject
    Messages message;

    @Inject
    UsuarioRepository usuarioRepository;

    private Usuario findUserById(Long id) {
        Optional<Usuario> usuarioEncontrado =  usuarioRepository.findByIdOptional(id);
        return usuarioEncontrado.orElseThrow(() -> new NotFoundException(message.getMessage("usuario.nao.encontrado.id", id)));
    }

    public Usuario findByEmail(String email) {
        Optional<Usuario> usuarioEncontrado =  usuarioRepository.findByEmail(email);
        return usuarioEncontrado.orElseThrow(() -> new NotFoundException(message.getMessage("usuario.nao.encontrado.email", email)));
    }

    public List<UsuarioDTO> listar(FiltroUsuarioDTO filtro, Integer page, Integer size) {
        return mapper.toList(usuarioRepository.findPage(filtro, page, size), UsuarioDTO.class);
    }

    public Long count(FiltroUsuarioDTO filtro) {
        return usuarioRepository.count(filtro);
    }

    public UsuarioDTO recuperar(Long id) {
        Usuario usuarioEncontrado = findUserById(id);
        return mapper.toObject(usuarioEncontrado, UsuarioDTO.class);
    }

    public UsuarioDTO criar(CriarUsuarioDTO usuario) {
        Usuario novoUsuario = mapper.toObject(usuario, Usuario.class);
        novoUsuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return mapper.toObject(usuarioRepository.create(novoUsuario), UsuarioDTO.class);
    }

    public UsuarioDTO atualizar(CriarUsuarioDTO usuario, Long id) throws IllegalAccessException, InvocationTargetException {
        Usuario usuarioEncontrado =  findUserById(id);
        BeanUtils.copyProperties(usuarioEncontrado, usuario);
        Usuario usuarioAtualizado = usuarioRepository.update(usuarioEncontrado);
        return  mapper.toObject(usuarioAtualizado, UsuarioDTO.class);
    }

    public void deletar(Long id) {
        findUserById(id);
        usuarioRepository.delete(id);
    }
}
