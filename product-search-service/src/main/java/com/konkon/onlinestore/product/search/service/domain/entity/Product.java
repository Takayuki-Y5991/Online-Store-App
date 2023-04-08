package com.konkon.onlinestore.product.search.service.domain.entity;

import com.konkon.onlinestore.product.search.service.domain.value.Price;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Product {
    private UUID id;
    private String name;
    private String description;
    private Price price;
    private String imageUrl;
    private int version;
    private Category category;

    public static Product build(
            UUID id,
            String name,
            String description,
            Price price,
            String imageUrl,
            int version,
            Category category
    ) {
        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .description(description)
                .imageUrl(imageUrl)
                .version(version)
                .category(category)
                .build();
    }

    public static Product createBuild(
            String name,
            String description,
            Price price,
            String imageUrl,
            Category category
    ) {
        return Product.builder()
                .id(UUID.randomUUID())
                .name(name)
                .description(description)
                .price(price)
                .imageUrl(imageUrl)
                .version(1)
                .category(category)
                .build();
    }
}