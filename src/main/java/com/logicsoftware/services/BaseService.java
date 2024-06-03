package com.logicsoftware.services;

import java.util.Optional;

import com.logicsoftware.models.Usuario;
import com.logicsoftware.utils.auth.TokenUtils;

import io.quarkus.vertx.http.runtime.CurrentVertxRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
public class BaseService {

    @Inject
    protected CurrentVertxRequest request;

    private String extractTokenFromRequest() {
        Optional<String> authorization = Optional.of(request.getCurrent().request().headers().get("Authorization"));
        return authorization.orElseThrow(() -> new BadRequestException("Token not found"));
    }

    protected Usuario getUser() {
        String token = extractTokenFromRequest();
        return TokenUtils.getUsuario(token.substring(TokenUtils.TOKEN_TYPE.length()).trim());
    }
}
