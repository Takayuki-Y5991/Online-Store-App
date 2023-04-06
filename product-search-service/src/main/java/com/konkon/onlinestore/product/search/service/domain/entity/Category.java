package com.konkon.onlinestore.product.search.service.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Category {
    private Integer id;
    private String name;
}
