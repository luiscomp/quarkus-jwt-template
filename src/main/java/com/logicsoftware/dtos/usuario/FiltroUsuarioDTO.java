package com.logicsoftware.dtos.usuario;

import com.logicsoftware.dtos.IdentityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representação para filtrar um usuário")
public class FiltroUsuarioDTO {
    @Schema(description = "Nome", example = "Luis Eduardo M. Ferreira")
    private String nome;
    
    @Schema(description = "E-mail", example = "luizeduardo354@gmail.com")
    private String email;

    @Schema(description = "Perfil")
    private IdentityDTO perfil;
}
