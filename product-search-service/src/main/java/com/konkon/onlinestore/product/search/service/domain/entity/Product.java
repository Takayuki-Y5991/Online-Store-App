package com.konkon.onlinestore.product.search.service.domain.entity;

import com.konkon.onlinestore.product.search.service.domain.value.Price;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Product {
    private UUID id;
    private String name;
    private String description;
    private Price price;
    private String imageUrl;
    private Category category;
}