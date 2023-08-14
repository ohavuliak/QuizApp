package com.example.quizapp.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface EntityMapper<E, D> {
    E toEntity (D dto);
    D toDto (E entity);
    List<E> toListEntity(List<D> listDto);
    List<D> toListDto(List<E> listEntity);
    void updateEntity(@MappingTarget E entity1, E entity2);
}
