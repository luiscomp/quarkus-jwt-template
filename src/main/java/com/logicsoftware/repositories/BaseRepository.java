package com.logicsoftware.repositories;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.logicsoftware.utils.database.Filter;
import com.logicsoftware.utils.database.IgnoreCase;
import com.logicsoftware.utils.database.Pageable;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;

public class BaseRepository<T> implements PanacheRepository<T> {
    
    public PanacheQuery<T> aplicarFiltros(Object filtro) {
        String query = "";
        Parameters parameters = new Parameters();
    
        for (Field field : getAllFields(filtro.getClass())) {
            try {
                field.setAccessible(true);
                Object value = field.get(filtro);
                if (Objects.nonNull(value) && !aninhado(field)) {
                    query += aplicarFiltroNoCampo(field, value, query.isEmpty());
                    parameters.and(field.getName(), aplicarValorNoFiltro(field, value));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    
        return find(query, parameters);
    }

    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            fields.addAll(getAllFields(clazz.getSuperclass()));
        }
        return fields;
    }

    private String aplicarFiltroNoCampo(Field field, Object value, Boolean isFirstField) {
        if (field.isAnnotationPresent(IgnoreCase.class)) {
            return (isFirstField ? "" : " OR ") + " UPPER(" + field.getName() + ") LIKE CONCAT('%', :" + field.getName() + ", '%')";
        }
        if (String.class.isAssignableFrom(field.getType())) {
            return (isFirstField ? "" : " OR ") + field.getName() + " LIKE CONCAT('%', :" + field.getName() + ", '%')";
        } else {
            return (isFirstField ? "" : " OR ") + field.getName() + " = :" + field.getName();
        }
    }

    private Object aplicarValorNoFiltro(Field field, Object value) {
        if (field.isAnnotationPresent(IgnoreCase.class)) {
            return value.toString().toUpperCase();
        }
        return value;
    }

    private boolean aninhado(Field field) {
        return field.getType().isAnnotationPresent(Filter.class);
    }

    public Pageable<T> findPage(Object filter, Integer page, Integer size) {
        Pageable.PageableBuilder<T> builder = Pageable.builder();

        PanacheQuery<T> query = aplicarFiltros(filter);
        query.page(Page.of(page - 1, size));

        builder.page(query.list());
        builder.totalElements(query.count());
        builder.pageSize(size);

        return builder.build();
    }
}
