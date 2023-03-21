package com.konkon.onlinestore.product.search.service.domain.entity;

import com.konkon.onlinestore.product.search.service.domain.value.Category;
import com.konkon.onlinestore.product.search.service.domain.value.ProductId;

import java.math.BigDecimal;
import java.util.List;

public class Product{
    private ProductId id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private List<Category> category;
}