package com.example.quizapp.mapper;

import com.example.quizapp.dto.PageDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")

public abstract class PageMapper {
     public <D> PageDTO<D> toPageDto(Page<D> page){
         PageDTO<D> pageDto = new PageDTO<>();
         pageDto.setLimit(page.getSize());
         pageDto.setTotalItems(page.getTotalElements());
         pageDto.setPage(page.getNumber());
         pageDto.setTotalPages(page.getTotalPages());
         pageDto.setData(page.getContent());
         return pageDto;
     }
}
