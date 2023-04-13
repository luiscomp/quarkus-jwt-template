package com.logicsoftware.dtos.perfil;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Representação de um perfil")
public class PerfilDTO {
    @Schema(description = "Identificação", example = "1")
    private Long id;
    
    @Schema(description = "Nome", example = "ADMIN")
    private String nome;
}
