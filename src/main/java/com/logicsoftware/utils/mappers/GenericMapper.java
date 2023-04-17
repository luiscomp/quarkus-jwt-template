package com.logicsoftware.utils.mappers;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.modelmapper.ModelMapper;

import com.logicsoftware.config.Beans;
import com.logicsoftware.utils.database.Pageable;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@ApplicationScoped
public class GenericMapper implements IGenericMapper {

    @Inject
    @NonNull
    ModelMapper mapper;

    public static GenericMapper getInstance() {
        return new GenericMapper(new Beans().modelMapper());
    }

    @Override
    public <T> T toObject(Object obj, Class<T> clazz) {
        if(Objects.isNull(obj)) return null;
        return mapper.map(obj, clazz);
    }

    @Override
    public <T> List<T> toList(List<?> list, Class<T> clazz) {
        if(Objects.isNull(list) || list.isEmpty()) return Collections.emptyList();
        return list.stream().map(obj -> toObject(obj, clazz)).collect(Collectors.toList());
    }

    @Override
    public <T> Pageable<T> toPageable(Pageable<?> pageable, Class<T> clazz) {
        if(Objects.isNull(pageable)) return Pageable.<T>builder().build();
        List<T> mappedPage = pageable.getPage().stream().map(obj -> toObject(obj, clazz)).collect(Collectors.toList());
        return Pageable.<T>builder()
                .page(mappedPage)
                .pageSize(pageable.getPageSize())
                .totalElements(pageable.getTotalElements())
                .build();
    }
    
}
