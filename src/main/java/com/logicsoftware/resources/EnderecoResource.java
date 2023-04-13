package com.logicsoftware.resources;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.logicsoftware.dtos.endereco.ViaCepResponseDTO;
import com.logicsoftware.services.EnderecoService;
import com.logicsoftware.utils.enums.AppStatus;
import com.logicsoftware.utils.request.DataResponse;

@Tag(name = "Endereço")
@Path("/endereco")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService service;

    @RolesAllowed("ADMIN")
    @GET
    @Path("/cep/{cep}")
    @Operation(operationId = "getAddressByCep", summary = "Obter um endereço pelo CEP", description = "Digite um CEP para obter um endereço")
    @Parameters({
            @Parameter(name = "cep", description = "CEP do endereço", required = true, example = "64033660"),
    })
    public DataResponse<ViaCepResponseDTO> getAddressByCep(@PathParam("cep") String cep) {
        DataResponse.DataResponseBuilder<ViaCepResponseDTO> response = DataResponse.builder();
        response.data(service.getAddressByCep(cep));
        response.status(AppStatus.SUCCESS);
        return response.build();
    }
}
