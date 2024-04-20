package com.logicsoftware.dtos;

import com.logicsoftware.utils.database.Filter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;

@Data
@Filter
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representação para filtrar Base DTO")
public class BaseDTO {
    @Schema(description = "Criado em dd/MM/yyyy", example = "14/04/2023")
    private ZonedDateTime criadoEm;

    @Schema(description = "Inativo", example = "false", defaultValue = "false")
    private Boolean inativo;
}
