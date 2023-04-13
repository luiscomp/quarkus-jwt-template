package com.logicsoftware.dtos.perfil;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representação para filtrar um perfil")
public class FiltroPerfilDTO {
    @Schema(description = "Nome", example = "ADMIN")
    private String nome;
}
