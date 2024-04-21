package com.logicsoftware.dtos.usuario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.logicsoftware.dtos.perfil.PerfilDTO;
import com.logicsoftware.utils.database.Filter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data @Filter
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
