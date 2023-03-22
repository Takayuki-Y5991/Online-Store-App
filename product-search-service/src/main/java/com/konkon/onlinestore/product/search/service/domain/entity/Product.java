package com.konkon.onlinestore.product.search.service.domain.entity;

import com.konkon.onlinestore.product.search.service.domain.value.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Product {
    private UUID id;
    private String name;
    private String description;
    private Price price;
    private String imageUrl;
    private Set<Category> categories;
}