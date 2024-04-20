package com.logicsoftware.resources;

import com.logicsoftware.services.AuthenticationService;
import com.logicsoftware.utils.auth.AuthRequest;
import com.logicsoftware.utils.auth.AuthResponse;
import com.logicsoftware.utils.request.DataResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.Form;

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

    @PermitAll
    @GET
    @Path("/encode-password")
    @Operation(operationId = "encodePassword", description = "Gera uma senha criptografada", summary = "Gera uma senha criptografada")
    @Parameters({
            @Parameter(name = "senha", description = "Senha a ser criptografada", example = "123456", required = true)
    })
    public DataResponse<String> encodePassword(@QueryParam("senha") String senha) {
        return DataResponse.ok(service.encodePassword(senha), String.class);
    }
}
