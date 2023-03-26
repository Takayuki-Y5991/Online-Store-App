package com.konkon.onlinestore.product.search.service.infrastructure.datasource.config;

import com.konkon.onlinestore.product.search.service.infrastructure.datasource.config.base.Transaction;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.function.Function;

@ApplicationScoped
public class PgPoolTransaction implements Transaction {

    private final io.vertx.mutiny.sqlclient.Transaction vertxTransaction;

    @Inject
    public PgPoolTransaction(io.vertx.mutiny.sqlclient.Transaction vertxTransaction) {
        this.vertxTransaction = vertxTransaction;
    }

    @Override
    public <T> Uni<T> execute(Function<Transaction, Uni<T>> operation) {
        return operation.apply(this);
    }

    @Override
    public Uni<Void> commit() {
        return vertxTransaction.commit();
    }

    @Override
    public Uni<Void> rollback() {
        return vertxTransaction.rollback();
    }
}
