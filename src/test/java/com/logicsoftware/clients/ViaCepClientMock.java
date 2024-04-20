package com.logicsoftware.clients;

import com.logicsoftware.dtos.endereco.ViaCepResponseDTO;
import com.logicsoftware.mocks.EnderecoResourceMocks;
import io.quarkus.test.Mock;
import org.eclipse.microprofile.rest.client.inject.RestClient;


@Mock
@RestClient
public class ViaCepClientMock implements ViaCepClient {

    @Override
    public ViaCepResponseDTO getAddressByCep(String cep) {
        return EnderecoResourceMocks.viaCepAddress();
    }
}
