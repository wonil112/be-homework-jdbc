package com.springboot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponseDto<T> {

    private List<T> data;
    private PageInfo pageInfo;
}
