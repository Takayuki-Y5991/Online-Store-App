package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions.command;

public class CategoryCommand {

    public static final String FETCH = "SELECT * FROM categories ORDER BY id";
    public static final String FETCH_BY_ID = "SELECT * FROM categories WHERE id = $1";
    public static final String INSERT = "INSERT INTO categories (name) VALUES ($1)";
    public static final String DELETE = "DELETE FROM categories WHERE id = $1";
}
