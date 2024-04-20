package com.logicsoftware.dtos.usuario;

import com.logicsoftware.dtos.IdentityDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representação para criar um usuário")
public class CriarUsuarioDTO {
    @NotEmpty(message = "Nome é obrigatório")
    @Schema(description = "Nome", example = "Luis Eduardo M. Ferreira", required = true)
    private String nome;

    @NotEmpty(message = "E-mail é obrigatório")
    @Schema(description = "E-mail", example = "luizeduardo354@gmail.com", required = true)
    private String email;

    @NotEmpty(message = "Senha é obrigatória")
    @Schema(description = "Senha", example = "123456", required = true)
    private String senha;

    @NotNull(message = "Perfil é obrigatório")
    @Schema(description = "Perfil", required = true)
    private IdentityDTO perfil;
}
