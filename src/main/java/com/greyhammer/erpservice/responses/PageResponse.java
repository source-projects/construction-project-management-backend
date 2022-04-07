package com.greyhammer.erpservice.responses;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PageResponse<T> {
    @JsonView(Object.class)
    private Set<T> results;
    @JsonView(Object.class)
    private int page;
    @JsonView(Object.class)
    private int count;
    @JsonView(Object.class)
    private long total;
}
