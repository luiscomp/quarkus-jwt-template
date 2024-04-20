package com.logicsoftware.utils.database;

import lombok.*;

import java.util.List;

@Data
@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class Pageable<T> {
    private List<T> page;
    private Long totalElements;
    @Getter(AccessLevel.NONE)
    private Integer totalPages;
    private Integer pageSize;

    public Long getTotalPages() {
        return (long) Math.ceil((double) totalElements / pageSize);
    }
}
