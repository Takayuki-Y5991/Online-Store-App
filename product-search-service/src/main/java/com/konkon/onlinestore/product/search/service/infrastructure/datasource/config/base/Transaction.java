package com.konkon.onlinestore.product.search.service.infrastructure.datasource.config.base;

import io.smallrye.mutiny.Uni;

import java.util.function.Function;

public interface Transaction {
    <T> Uni<T> execute(Function<Transaction, Uni<T>> operation);

    Uni<Void> commit();

    Uni<Void> rollback();

}
