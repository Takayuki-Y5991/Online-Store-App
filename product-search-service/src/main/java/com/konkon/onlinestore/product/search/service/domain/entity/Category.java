package com.konkon.onlinestore.product.search.service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Category {
    private Integer id;
    private String name;
}
