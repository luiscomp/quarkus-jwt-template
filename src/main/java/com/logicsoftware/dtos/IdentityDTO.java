package com.logicsoftware.dtos;

import javax.validation.constraints.NotEmpty;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.logicsoftware.utils.database.Filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Filter
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representação para criar um relacionamento")
public class IdentityDTO {
    @NotEmpty(message = "Id de relacionamento é obrigatório")
    @Schema(description = "Id de relacionamento", example = "1", required = true)
    private Long id;
}
