package com.konkon.onlinestore.product.search.service.infrastructure.mapper;
import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.infrastructure.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ProductMapper {

    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product product);
}
