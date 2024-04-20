package com.logicsoftware.dtos.perfil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representação para filtrar um perfil")
public class FiltroPerfilDTO {
    @Schema(description = "Nome", example = "ADMIN")
    private String nome;
}
