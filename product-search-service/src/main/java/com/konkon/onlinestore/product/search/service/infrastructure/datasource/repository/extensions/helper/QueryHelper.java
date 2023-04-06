package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions.helper;

import java.util.Objects;

public class QueryHelper {
    /**
     * PgPoolライブラリのプレースフォルダにORDERの関連句挿入ができなかったため<br/>
     * Orderの順序に関して定義する関数
     *
     * @param baseQuery 「%s」を含むQuery
     * @param order     順序 [ASC or DESC]
     * @return Query
     */
    public static String setOrderInQuery(String baseQuery, String order) {
        String finalOrder = Objects.isNull(order) ? "ASC" : order;
        return String.format(baseQuery, finalOrder);
    }
}
