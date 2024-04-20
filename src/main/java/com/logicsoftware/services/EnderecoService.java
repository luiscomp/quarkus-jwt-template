package com.logicsoftware.services;

import com.logicsoftware.clients.ViaCepClient;
import com.logicsoftware.dtos.endereco.ViaCepResponseDTO;
import io.quarkus.arc.log.LoggerName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class EnderecoService {
    
    @LoggerName("address-service")
    Logger logger;

    @Inject
    @RestClient
    ViaCepClient viaCepClient;

    public ViaCepResponseDTO getAddressByCep(String cep) {
        return viaCepClient.getAddressByCep(cep);
    }
}
