package com.example.quizapp.mapper;

import java.util.List;

public interface EntityMapper<E, D> {
    E toEntity (D dto);
    D toDto (E entity);
    List<E> toListEntity(List<D> listDto);
    List<D> toListDto(List<E> listEntity);
}
