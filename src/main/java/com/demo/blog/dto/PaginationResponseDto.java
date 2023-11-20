package com.demo.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PaginationResponseDto<T> {

    int pageNo;
    int pageSize;
    long totalElements;
    int totalPages;
    boolean isLastPage;
    List<T> content;
}
