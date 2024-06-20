package com.springboot.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PageInfo {
    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
}
