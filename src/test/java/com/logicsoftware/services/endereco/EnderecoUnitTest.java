package com.logicsoftware.services.endereco;

import com.logicsoftware.dtos.endereco.ViaCepResponseDTO;
import com.logicsoftware.mocks.EnderecoResourceMocks;
import com.logicsoftware.services.EnderecoService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class EnderecoUnitTest {

    @Inject
    EnderecoService addressService;

    @Test
    public void getAddressByCep() {
        ViaCepResponseDTO response = addressService.getAddressByCep("64033660");
        assertEquals(EnderecoResourceMocks.viaCepAddress(), response);
    }
}
