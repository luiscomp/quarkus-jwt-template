package com.logicsoftware.utils.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.logicsoftware.utils.database.Pageable;
import com.logicsoftware.utils.mappers.GenericMapper;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Data
@Builder(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Representação da página padrão de resposta do servidor")
public class PageResponse<T> {
    @Schema(description = "Resposta da página do servidor para solicitação chamada")
    private List<T> page;
    
    @Schema(description = "Total de elementos no banco de dados", example = "50")
    private Long totalElements;
    
    @Schema(description = "Total de páginas do tamanho passado na requisição", example = "5")
    @Getter(AccessLevel.NONE)
    private Integer totalPages;
    
    @Schema(description = "Total de elementos por página", example = "10")
    private Integer pageSize;

    public Long getTotalPages() {
        return (long) Math.ceil((double) totalElements / pageSize);
    }

    static public <T> PageResponse<T> ok(Pageable<?> page, Class<T> clazz) {
        GenericMapper mapper = GenericMapper.getInstance();

        return PageResponse.<T>builder()
                .page(mapper.toList(page.getPage(), clazz))
                .totalElements(page.getTotalElements())
                .pageSize(page.getPageSize())
                .build();
    }
}
