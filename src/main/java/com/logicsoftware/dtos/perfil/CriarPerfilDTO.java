package com.logicsoftware.dtos.perfil;

import javax.validation.constraints.NotEmpty;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representação para criar um perfil")
public class CriarPerfilDTO {
    @NotEmpty(message = "Nome é obrigatório")
    @Schema(description = "Nome", example = "ADMIN")
    private String nome;
}
