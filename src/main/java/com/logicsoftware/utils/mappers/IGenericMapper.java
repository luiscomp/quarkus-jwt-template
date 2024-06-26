package com.logicsoftware.utils.mappers;

import com.logicsoftware.utils.database.Pageable;

import java.util.List;

public interface IGenericMapper {
    <T> T toObject(Object obj, Class<T> clazz);
    <T> List<T> toList(List<?> list, Class<T> clazz);
    <T> Pageable<T> toPageable(Pageable<?> pageable, Class<T> clazz);
}

