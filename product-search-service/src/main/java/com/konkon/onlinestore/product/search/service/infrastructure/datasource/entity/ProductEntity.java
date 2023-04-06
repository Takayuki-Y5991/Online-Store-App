package com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    private UUID id;
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    private int version;
    private CategoryEntity category;
}
