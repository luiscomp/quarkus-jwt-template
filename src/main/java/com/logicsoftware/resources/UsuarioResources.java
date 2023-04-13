package com.logicsoftware.resources;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.logicsoftware.dtos.usuario.CriarUsuarioDTO;
import com.logicsoftware.dtos.usuario.FiltroUsuarioDTO;
import com.logicsoftware.dtos.usuario.UsuarioDTO;
import com.logicsoftware.services.UsuarioService;
import com.logicsoftware.utils.enums.AppStatus;
import com.logicsoftware.utils.request.DataResponse;
import com.logicsoftware.utils.request.PageResponse;

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
        PageResponse.PageResponseBuilder<UsuarioDTO> response = PageResponse.builder();
        response.page(service.listar(filter, page, size));
        response.totalElements(service.count(filter));
        response.pageSize(size);

        return response.build();
    }

    @GET
    @Path("/{id}")
    @Operation(operationId = "getUser", description = "Passar o id do usuário para encontrar um usuário", summary = "Obter um Usuário")
    @Parameters({
        @Parameter(name = "id", description = "id do Usuário", example = "1", required = true)
    })
    public DataResponse<UsuarioDTO> buscar(@PathParam("id") Long id) {
        DataResponse.DataResponseBuilder<UsuarioDTO> response = DataResponse.builder();
        response.data(service.recuperar(id));
        response.status(AppStatus.SUCCESS);
        return response.build();
    }

    @POST
    @Operation(operationId = "createUser", description = "Criar um novo Usuário", summary = "Criar um novo Usuário")
    @Parameters({
        @Parameter(name = "version", description = "Version of the API", required = true, example = "1")
    })
    @Transactional
    public DataResponse<UsuarioDTO> criar(@Valid CriarUsuarioDTO usuario) {
        DataResponse.DataResponseBuilder<UsuarioDTO> response = DataResponse.builder();
        response.data(service.criar(usuario));
        response.status(AppStatus.SUCCESS);
        return response.build();
    }

    @PUT
    @Path("/{id}")
    @Operation(operationId = "updateUser", description = "Atualizar um Usuário pelo id", summary = "Atualizar um Usuário")
    @Parameters({
        @Parameter(name = "id", description = "id do Usuário", example = "1", required = true)
    })
    @Transactional
    public DataResponse<UsuarioDTO> atualizar(@Valid CriarUsuarioDTO usuario, @PathParam("id") Long id) throws IllegalAccessException, InvocationTargetException {
        DataResponse.DataResponseBuilder<UsuarioDTO> response = DataResponse.builder();
        response.data(service.atualizar(usuario, id));
        response.status(AppStatus.SUCCESS);
        return response.build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(operationId = "deleteUser", summary = "Apagar um Usuário", description = "Deletar um usuário pelo id")
    @Parameters({
        @Parameter(name = "id", description = "id do Usuário", required = true, example = "1")
    })
    @Transactional
    public DataResponse<Void> delete(@PathParam("id") Long id) {
        DataResponse.DataResponseBuilder<Void> response = DataResponse.builder();
        service.deletar(id);
        response.status(AppStatus.SUCCESS);
        return response.build();
    }
}