package com.valka.guild_service.model.dto;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse {
    private int pageNumber;
    private int pageSize;
    private long totalPages;
    private long totalElements;
    private long number;
    private long size;
    private boolean first;
    private boolean last;

    public PageResponse(PageResponse other){
        this.pageNumber = other.getPageNumber();
        this.pageSize = other.getPageNumber();
        this.totalPages = other.getTotalPages();
        this.totalElements = other.getTotalElements();
        this.number = other.getNumber();
        this.size = other.getSize();
        this.first = other.isFirst();
        this.last = other.isLast();
    }

    public static <T> PageResponse from(Page<T> page){
        return PageResponse.builder()
                .pageNumber(page.getPageable().getPageNumber())
                .pageSize(page.getPageable().getPageSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .number(page.getNumber())
                .size(page.getSize())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}

