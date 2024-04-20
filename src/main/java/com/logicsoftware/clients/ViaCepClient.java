package com.logicsoftware.clients;

import com.logicsoftware.dtos.endereco.ViaCepResponseDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/ws")
@RegisterRestClient(baseUri = "https://viacep.com.br")
public interface ViaCepClient {
    
    @GET
    @Path("/{cep}/json")
    @Produces(MediaType.APPLICATION_JSON)
    public ViaCepResponseDTO getAddressByCep(@PathParam("cep") String cep);
}
