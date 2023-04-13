package com.logicsoftware.clients;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.logicsoftware.dtos.endereco.ViaCepResponseDTO;
import com.logicsoftware.mocks.EnderecoResourceMocks;

import io.quarkus.test.Mock;


@Mock
@RestClient
public class ViaCepClientMock implements ViaCepClient {

    @Override
    public ViaCepResponseDTO getAddressByCep(String cep) {
        return EnderecoResourceMocks.viaCepAddress();
    }
}
