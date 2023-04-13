package com.logicsoftware.dtos.endereco;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Representação de um endereço")
public class ViaCepResponseDTO {
    @Schema(description = "CEP", example = "01001000")
    private String cep;

    @Schema(description = "Logradouro", example = "Praça da Sé")
    private String logradouro;

    @Schema(description = "Complemento", example = "lado ímpar")
    private String complemento;

    @Schema(description = "Bairro", example = "Sé")
    private String bairro;

    @Schema(description = "Localidade", example = "São Paulo")
    private String localidade;

    @Schema(description = "UF", example = "SP")
    private String uf;

    @Schema(description = "ibge", example = "")
    private String ibge;

    @Schema(description = "gia", example = "")
    private String gia;

    @Schema(description = "ddd", example = "11")
    private String ddd;

    @Schema(description = "siafi", example = "")
    private String siafi;
}
