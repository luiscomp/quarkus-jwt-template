package com.logicsoftware.resources;

import com.logicsoftware.dtos.usuario.CriarUsuarioDTO;
import com.logicsoftware.dtos.usuario.FiltroUsuarioDTO;
import com.logicsoftware.dtos.usuario.UsuarioDTO;
import com.logicsoftware.services.UsuarioService;
import com.logicsoftware.utils.request.DataResponse;
import com.logicsoftware.utils.request.PageResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.lang.reflect.InvocationTargetException;

@Tag(name = "Usuário")
@Path("/usuario")
@RolesAllowed("ADMIN")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResources {

    @Inject
    UsuarioService service;

    @POST
    @Path("/listar")
    @Operation(operationId = "getUsers", description = "Use os parâmetros definidos para listar uma página específica de usuários.", summary = "Listar uma página de usuário")
    @Parameters({
        @Parameter(name = "page", description = "Número da página", example = "1", required = true),
        @Parameter(name = "size", description = "Tamanho da página", example = "10", required = true)
    })
    public PageResponse<UsuarioDTO> listar(FiltroUsuarioDTO filter, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        return PageResponse.ok(service.listar(filter, page, size), UsuarioDTO.class);
    }

    @GET
    @Path("/{id}")
    @Operation(operationId = "getUser", description = "Passar o id do usuário para encontrar um usuário", summary = "Obter um Usuário")
    @Parameters({
        @Parameter(name = "id", description = "id do Usuário", example = "1", required = true)
    })
    public DataResponse<UsuarioDTO> buscar(@PathParam("id") Long id) {
        return DataResponse.ok(service.recuperar(id), UsuarioDTO.class);
    }

    @POST
    @Operation(operationId = "createUser", description = "Criar um novo Usuário", summary = "Criar um novo Usuário")
    @Parameters({
        @Parameter(name = "version", description = "Version of the API", required = true, example = "1")
    })
    @Transactional
    public DataResponse<UsuarioDTO> criar(@Valid CriarUsuarioDTO usuario) {
        return DataResponse.ok(service.criar(usuario), UsuarioDTO.class);
    }

    @PUT
    @Path("/{id}")
    @Operation(operationId = "updateUser", description = "Atualizar um Usuário pelo id", summary = "Atualizar um Usuário")
    @Parameters({
        @Parameter(name = "id", description = "id do Usuário", example = "1", required = true)
    })
    @Transactional
    public DataResponse<UsuarioDTO> atualizar(@Valid CriarUsuarioDTO usuario, @PathParam("id") Long id) throws IllegalAccessException, InvocationTargetException {
        return DataResponse.ok(service.atualizar(usuario, id), UsuarioDTO.class);
    }

    @DELETE
    @Path("/{id}")
    @Operation(operationId = "deleteUser", summary = "Apagar um Usuário", description = "Deletar um usuário pelo id")
    @Parameters({
        @Parameter(name = "id", description = "id do Usuário", required = true, example = "1")
    })
    @Transactional
    public DataResponse<Void> delete(@PathParam("id") Long id) {
        service.deletar(id);
        return DataResponse.ok();
    }
}