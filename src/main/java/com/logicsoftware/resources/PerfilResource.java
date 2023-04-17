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

import com.logicsoftware.dtos.perfil.CriarPerfilDTO;
import com.logicsoftware.dtos.perfil.FiltroPerfilDTO;
import com.logicsoftware.dtos.perfil.PerfilDTO;
import com.logicsoftware.services.PerfilService;
import com.logicsoftware.utils.request.DataResponse;
import com.logicsoftware.utils.request.PageResponse;

@Tag(name = "Perfil")
@Path("/perfil")
@RolesAllowed("ADMIN")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PerfilResource {
    
    @Inject
    PerfilService service;

    @POST
    @Path("/listar")
    @Operation(operationId = "getAddress", description = "Use os parâmetros definidos para listar uma página específica de perfis.", summary = "Listar uma página de perfil")
    @Parameters({
        @Parameter(name = "page", description = "Número da página", example = "1", required = true),
        @Parameter(name = "size", description = "Tamanho da página", example = "10", required = true)
    })
    public PageResponse<PerfilDTO> listar(FiltroPerfilDTO filter, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        return PageResponse.ok(service.listar(filter, page, size), PerfilDTO.class);
    }

    @GET
    @Path("/{id}")
    @Operation(operationId = "getPerfil", description = "Passar o id do perfil para encontrar um perfil", summary = "Obter um Perfil")
    @Parameters({
        @Parameter(name = "id", description = "id do Perfil", example = "1", required = true)
    })
    public DataResponse<PerfilDTO> buscar(@PathParam("id") Long id) {
        return DataResponse.ok(service.recuperar(id), PerfilDTO.class);
    }

    @POST
    @Operation(operationId = "createPerfil", description = "Criar um novo Perfil", summary = "Criar um novo Perfil")
    @Parameters({
        @Parameter(name = "version", description = "Version of the API", required = true, example = "1")
    })
    @Transactional
    public DataResponse<PerfilDTO> criar(@Valid CriarPerfilDTO perfil) {
        return DataResponse.ok(service.criar(perfil), PerfilDTO.class);
    }

    @PUT
    @Path("/{id}")
    @Operation(operationId = "updatePerfil", description = "Atualizar um Perfil pelo id", summary = "Atualizar um Perfil")
    @Parameters({
        @Parameter(name = "id", description = "id do Perfil", example = "1", required = true)
    })
    @Transactional
    public DataResponse<PerfilDTO> atualizar(@Valid CriarPerfilDTO perfil, @PathParam("id") Long id) throws IllegalAccessException, InvocationTargetException {
        return DataResponse.ok(service.atualizar(perfil, id), PerfilDTO.class);
    }

    @DELETE
    @Path("/{id}")
    @Operation(operationId = "deletePerfil", summary = "Apagar um Perfil", description = "Deletar um perfil pelo id")
    @Parameters({
        @Parameter(name = "id", description = "id do Perfil", required = true, example = "1")
    })
    @Transactional
    public DataResponse<Void> delete(@PathParam("id") Long id) {
        service.deletar(id);
        return DataResponse.ok();
    }
}
