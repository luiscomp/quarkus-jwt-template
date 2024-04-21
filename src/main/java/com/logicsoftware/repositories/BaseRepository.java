package com.logicsoftware.repositories;

import com.logicsoftware.utils.GenericUtils;
import com.logicsoftware.utils.database.Filter;
import com.logicsoftware.utils.database.IgnoreCase;
import com.logicsoftware.utils.database.Pageable;
import com.logicsoftware.utils.database.WhereType;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public class BaseRepository<T> implements PanacheRepository<T> {

    private boolean isNested(Field field) {
        return field.getType().isAnnotationPresent(Filter.class);
    }

    private Object applyFilterValue(Field field, Object value) {
        if (field.isAnnotationPresent(IgnoreCase.class)) {
            return value.toString().toUpperCase();
        }
        return value;
    }

    protected void addWhereCondition(StringBuilder query, WhereType whereType, String condition) {
        String orderClause = "";
        int orderIndex = query.toString().toUpperCase().indexOf("ORDER BY");

        if(orderIndex != -1) {
            orderClause = query.substring(orderIndex, query.length());
            query.delete(orderIndex, query.length());
        }

        if (query.toString().toUpperCase().lastIndexOf("WHERE") > query.toString().toUpperCase().lastIndexOf("FROM")) {
            query.append(" ").append(whereType).append(" ").append(condition);
        } else {
            query.append(" WHERE ").append(condition);
        }

        if(orderIndex != -1) {
            query.append(orderClause);
        }
    }

    private void applyWhere(StringBuilder query, Field field, WhereType whereType, int currentJoin) {
        if (field.isAnnotationPresent(IgnoreCase.class)) {
            addWhereCondition(query, whereType,"UPPER(e" + currentJoin + "." + field.getName() + ") LIKE CONCAT('%', :" + "e" + currentJoin + "_" + field.getName() + ", '%')");
        }
        if (String.class.isAssignableFrom(field.getType())) {
            addWhereCondition(query, whereType,"e" + currentJoin + "." + field.getName() + " LIKE CONCAT('%', :" + "e" + currentJoin + "_" + field.getName() + ", '%')");
        } else {
            addWhereCondition(query, whereType,"e" + currentJoin + "." + field.getName() + " = :" + "e" + currentJoin + "_" + field.getName());
        }
    }

    private void applyWhereJoin(Field field, Object value, StringBuilder query, WhereType whereType, Parameters parameters, int currentJoin) {
        for (Field fieldT : GenericUtils.getAllFields(field.getType())) {
            try {
                fieldT.setAccessible(true);
                Object valueT = fieldT.get(value);
                if (Objects.nonNull(valueT) && !isNested(fieldT)) {
                    applyWhere(query, fieldT, whereType, currentJoin);
                    parameters.and("e" + currentJoin + "_" + fieldT.getName(), applyFilterValue(fieldT, valueT));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void applyJoin(Field field, Object value, StringBuilder query, Parameters parameters, WhereType whereType, int currentJoin) {
        query.append(" JOIN ").append("e").append(currentJoin - 1).append(".").append(field.getName()).append(" e").append(currentJoin);
        applyWhereJoin(field, value, query, whereType, parameters, currentJoin);
    }

    @SuppressWarnings("unchecked")
    private String applyJpqlQueryWithJoin(Object filter, Parameters parameters, int currentJoin, WhereType whereType) {
        T entity = (T) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        StringBuilder query = new StringBuilder("SELECT ").append("e").append(currentJoin).append(" FROM ").append(((Class<?>) entity).getSimpleName()).append(" e").append(currentJoin);

        for (Field field : GenericUtils.getAllFields(filter.getClass())) {
            try {
                field.setAccessible(true);
                Object value = field.get(filter);
                if (Objects.nonNull(value) && isNested(field) && GenericUtils.fieldHasNonNullProperty(value)) {
                    applyJoin(field, value, query, parameters, whereType, ++currentJoin);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return query.toString();
    }

    public PanacheQuery<T> produceQueryByFilter(Object filter, WhereType whereType) {
        int currentJoin = 1;
        Parameters parameters = new Parameters();
        StringBuilder query = new StringBuilder(applyJpqlQueryWithJoin(filter, parameters, currentJoin, whereType));

        for (Field field : GenericUtils.getAllFields(filter.getClass())) {
            try {
                field.setAccessible(true);
                Object value = field.get(filter);
                if (Objects.nonNull(value) && !isNested(field)) {
                    applyWhere(query, field, WhereType.AND, 1);
                    parameters.and("e1_" + field.getName(), applyFilterValue(field, value));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return find(query.toString(), parameters);
    }

    public Pageable<T> findPage(Object filter, Integer page, Integer size) {
        Pageable.PageableBuilder<T> builder = Pageable.builder();

        PanacheQuery<T> query = produceQueryByFilter(filter, WhereType.AND);
        query.page(Page.of(page - 1, size));

        builder.page(query.list());
        builder.totalElements(query.count());
        builder.pageSize(size);

        return builder.build();
    }
}
