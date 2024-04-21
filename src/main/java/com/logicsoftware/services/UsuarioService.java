package com.logicsoftware.services;

import com.logicsoftware.dtos.usuario.CriarUsuarioDTO;
import com.logicsoftware.dtos.usuario.UsuarioDTO;
import com.logicsoftware.models.Usuario;
import com.logicsoftware.repositories.UsuarioRepository;
import com.logicsoftware.utils.auth.PasswordEncoder;
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
public class UsuarioService {

    @LoggerName("UsuarioService")
    Logger logger;

    @Inject 
    PasswordEncoder passwordEncoder;

    @Inject
    GenericMapper mapper;

    @Inject
    Messages messages;

    @Inject
    UsuarioRepository usuarioRepository;

    private Usuario findUserById(Long id) {
        Optional<Usuario> usuarioEncontrado =  usuarioRepository.findByIdOptional(id);
        return usuarioEncontrado.orElseThrow(() -> new NotFoundException(messages.getMessage("usuario.nao.encontrado.id", id)));
    }

    public Usuario findByEmail(String email) {
        Optional<Usuario> usuarioEncontrado =  usuarioRepository.findByEmail(email);
        return usuarioEncontrado.orElseThrow(() -> new NotFoundException(messages.getMessage("usuario.nao.encontrado.email", email)));
    }

    public Pageable<Usuario> listar(UsuarioDTO filtro, int page, int size) {
        return usuarioRepository.findPage(filtro, page, size);
    }

    public Usuario recuperar(Long id) {
        return findUserById(id);
    }

    public Usuario criar(CriarUsuarioDTO usuario) {
        Usuario novoUsuario = mapper.toObject(usuario, Usuario.class);
        novoUsuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.persist(novoUsuario);
        return novoUsuario;
    }

    public Usuario atualizar(CriarUsuarioDTO usuario, Long id) throws IllegalAccessException, InvocationTargetException {
        Usuario usuarioEncontrado = findUserById(id);
        BeanUtils.copyProperties(usuarioEncontrado, usuario);
        usuarioRepository.persist(usuarioEncontrado);
        return usuarioEncontrado;
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
