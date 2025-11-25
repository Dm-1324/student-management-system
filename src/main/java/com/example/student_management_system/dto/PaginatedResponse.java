package com.example.student_management_system.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private List<T> content;
    private Long totalItems;
    private Integer totalPages;
    private Integer currentPage;
    private Integer pageSize;
    private Boolean isLast;

    public PaginatedResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalItems = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
        this.isLast = page.isLast();
    }
}