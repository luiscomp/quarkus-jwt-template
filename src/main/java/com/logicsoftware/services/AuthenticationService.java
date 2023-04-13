package com.logicsoftware.services;

import java.util.Collections;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import com.logicsoftware.models.Usuario;
import com.logicsoftware.utils.auth.AuthRequest;
import com.logicsoftware.utils.auth.AuthResponse;
import com.logicsoftware.utils.auth.GrantType;
import com.logicsoftware.utils.auth.PasswordEncoder;
import com.logicsoftware.utils.auth.TokenUtils;
import com.logicsoftware.utils.enums.EnumUtils;
import com.logicsoftware.utils.i18n.Messages;

import io.quarkus.arc.log.LoggerName;
import io.quarkus.security.UnauthorizedException;

@ApplicationScoped
public class AuthenticationService {

    @LoggerName("AuthenticationService")
    Logger logger;

    @Inject
    PasswordEncoder passwordEncoder;
    
    @Inject
    UsuarioService usuarioService;

    @Inject
    Messages message;

    @ConfigProperty(name = "com.logicsoftware.quarkusjwt.jwt.expire_in") 
    Long expireIn;

    @ConfigProperty(name = "com.logicsoftware.quarkusjwt.jwt.refresh_expire_in") 
    Long refreshExpireIn;
	
    @ConfigProperty(name = "mp.jwt.verify.issuer") 
    String issuer;

    public AuthResponse token(AuthRequest auth) {
        if(Objects.isNull(auth.getGrantType()))
            throw new BadRequestException(message.getMessage("auth.grant.type.invalido", EnumUtils.enumToString(GrantType.values())));

        switch (auth.getGrantType()) {
            case refresh_token:
                return refreshToken(auth.getRefreshToken());
            default:
                return accessToken(auth);
        }
    }

    public AuthResponse accessToken(AuthRequest auth) {
        try {
            Usuario usuario = usuarioService.findByEmail(auth.getUsername());
            if(!passwordEncoder.matches(auth.getPassword(), usuario.getSenha())) {
                throw new UnauthorizedException(message.getMessage("usuario.credenciais.invalidas"));
            }

            AuthResponse.AuthResponseBuilder builder = AuthResponse.builder()
                    .access_token(TokenUtils.getToken(auth.getUsername(), Collections.singleton(usuario.getPerfil().getNome()), expireIn, issuer))
                    .refresh_token(TokenUtils.getRefreshToken(auth.getUsername(), refreshExpireIn, issuer))
                    .expires_in(expireIn)
                    .refresh_expires_in(refreshExpireIn)
                    .token_type("Bearer");

            return builder.build();
        } catch (NotFoundException e) {
            throw new UnauthorizedException(message.getMessage("usuario.credenciais.invalidas"));
        }
    }

    public AuthResponse refreshToken(String refreshToken) {
        if(Objects.isNull(refreshToken) || refreshToken.isEmpty())
            throw new BadRequestException(message.getMessage("auth.token.invalido"));

        try {
            Usuario usuario = usuarioService.findByEmail(TokenUtils.getSubject(refreshToken));
            AuthResponse.AuthResponseBuilder builder = AuthResponse.builder()
                    .access_token(TokenUtils.getToken(usuario.getEmail(), Collections.singleton(usuario.getPerfil().getNome()), expireIn, issuer))
                    .refresh_token(TokenUtils.getRefreshToken(usuario.getEmail(), refreshExpireIn, issuer))
                    .expires_in(expireIn)
                    .refresh_expires_in(refreshExpireIn)
                    .token_type("Bearer");

            return builder.build();
        } catch (NotFoundException e) {
            throw new BadRequestException(message.getMessage("auth.token.invalido"));
        }
    }
}
