package com.example.quizapp.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
@Data
public class PageDTO<D> {
    private Integer limit;
    private Long totalItems;
    private Integer page;
    private Integer totalPages;
    private List<D> data;
}