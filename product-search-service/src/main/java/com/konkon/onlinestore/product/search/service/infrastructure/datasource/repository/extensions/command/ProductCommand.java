package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions.command;

/**
 * Productテーブルに関するSQLを管理する
 */
public class ProductCommand {
    public final static String FECTH = "SELECT p.*, c.name as category_name FROM products p INNER JOIN categories c ON p.category_id = c.id ORDER BY $1 %s LIMIT $2 OFFSET $3";
    public final static String FETCH_BY_ID = "SELECT p.*, c.name as category_name FROM products p INNER JOIN categories c ON p.category_id = c.id WHERE p.id = $1";
    public final static String INSERT = "INSERT INTO products VALUES ($1, $2, $3, $4, $5, $6, $7)";
    public final static String DELETE = "DELETE FROM products WHERE id = $1";
    public final static String UPDATE = "UPDATE products SET name = $1, price = $2, description = $3, image_url = $4, category_id = $5, version = version + 1 WHERE id = $6 AND version = $7";
}
