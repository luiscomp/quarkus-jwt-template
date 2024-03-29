package com.logicsoftware.dtos.usuario;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.logicsoftware.dtos.perfil.PerfilDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Representação de um usuário")
public class UsuarioDTO {
    @Schema(description = "Identificação", example = "1")
    private Long id;
    
    @Schema(description = "Nome", example = "Luis Eduardo M. Ferreira")
    private String nome;
    
    @Schema(description = "E-mail", example = "luizeduardo354@gmail.com")
    private String email;

    @Schema(description = "Perfil")
    private PerfilDTO perfil;
}
