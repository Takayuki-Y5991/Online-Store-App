package com.konkon.onlinestore.product.search.service.infrastructure.datasource.mapper;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.ProductEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.mapper.uses.ProductMapperUses;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi",
        uses = ProductMapperUses.class)
public interface ProductMapper {

    @Mapping(source = "price", target = "price", qualifiedByName = {"ProductMapperUses", "toPrice"})
    @Mapping(source = "category", target = "category", qualifiedByName = {"ProductMapperUses", "toCategory"})
    Product toDomain(ProductEntity entity);

    @Mapping(target = "price", qualifiedByName = {"ProductMapperUses", "fromPrice"})
    @Mapping(target = "category", qualifiedByName = {"ProductMapperUses", "fromCategory"})
    ProductEntity toEntity(Product domain);
}
