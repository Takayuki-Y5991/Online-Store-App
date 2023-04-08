package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions.command;

public class ReviewCommand {

    public static final String FETCH_BY_ID = "SELECT * FROM reviews where id = $1";
    public static final String FETCH_BY_PRODUCT_ID = "SELECT * FROM reviews WHERE product_id = $1";
    public static final String FETCH_BY_ACCOUNT_ID = "SELECT * FROM reviews WHERE account_uid = $1";
    public static final String INSERT = "INSERT INTO reviews VALUES ($1, $2, $3, $4, $5, $6, $7, $8)";
    public static final String DELETE = "DELETE FROM reviews WHERE id = $1";
    public static final String UPDATE = "UPDATE reviews SET rating = $1, title = $2, comment = $3, updated_at = $4 WHERE id = $5 AND updated_at = $6 AND account_uid = $7 AND product_id = $8";
}
