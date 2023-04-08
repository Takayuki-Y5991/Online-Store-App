package com.konkon.onlinestore.product.search.service.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Category {
    private Integer id;
    private String name;

    public static Category build(Integer id, String name) {
        return Category.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static Category createBuild(Integer id) {
        return Category.builder()
                .id(id)
                .name("CREATE")
                .build();
    }

    public static Category createBuild(String name) {
        return Category.builder()
                .id(0)
                .name(name)
                .build();
    }
}
