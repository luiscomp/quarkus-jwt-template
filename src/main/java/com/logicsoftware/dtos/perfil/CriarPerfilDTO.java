package com.logicsoftware.dtos.perfil;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representação para criar um perfil")
public class CriarPerfilDTO {
    @NotEmpty(message = "Nome é obrigatório")
    @Schema(description = "Nome", example = "ADMIN")
    private String nome;
}
