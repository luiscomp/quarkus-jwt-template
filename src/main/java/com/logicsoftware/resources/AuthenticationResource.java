package com.logicsoftware.resources;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.Form;

import com.logicsoftware.services.AuthenticationService;
import com.logicsoftware.utils.auth.AuthRequest;
import com.logicsoftware.utils.auth.AuthResponse;

@Tag(name = "Autenticação")
@Path("/auth")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    @Inject
    AuthenticationService service;

    @PermitAll
    @POST
    @Path("/token")
    @Operation(operationId = "token", description = "Autentica um usuário e retorna os tokens", summary = "Autentica um usuário")
    public AuthResponse token(@Form AuthRequest auth) {
        return service.token(auth);
    }    
}
