package com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    private Integer id;
    private String name;
}
