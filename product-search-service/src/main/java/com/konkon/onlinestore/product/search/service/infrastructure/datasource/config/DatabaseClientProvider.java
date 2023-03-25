package com.konkon.onlinestore.product.search.service.infrastructure.datasource.config;

import io.quarkus.arc.Unremovable;
import io.vertx.mutiny.pgclient.PgPool;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Unremovable
@ApplicationScoped
public class DatabaseClientProvider {

    private final PgPool client;

    @Inject
    public DatabaseClientProvider(PgPool client) {
        this.client = client;
    }

    public PgPool getClient() {
        return client;
    }
}
