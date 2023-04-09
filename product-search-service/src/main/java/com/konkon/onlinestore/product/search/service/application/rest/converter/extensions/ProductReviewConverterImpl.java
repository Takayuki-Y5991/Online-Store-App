package com.konkon.onlinestore.product.search.service.application.rest.converter.extensions;

import com.konkon.onlinestore.product.search.service.application.rest.converter.ProductConverter;
import com.konkon.onlinestore.product.search.service.application.rest.converter.ProductReviewConverter;
import com.konkon.onlinestore.product.search.service.application.rest.converter.ReviewConverter;
import com.konkon.onlinestore.product.search.service.application.rest.model.response.ProductReviewResponse;
import com.konkon.onlinestore.product.search.service.domain.entity.aggregation.ProductReview;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductReviewConverterImpl implements ProductReviewConverter {

    private final ProductConverter productConverter;
    private final ReviewConverter reviewConverter;

    @Inject
    public ProductReviewConverterImpl(ProductConverter productConverter, ReviewConverter reviewConverter) {
        this.productConverter = productConverter;
        this.reviewConverter = reviewConverter;
    }

    @Override
    public ProductReviewResponse toResponse(ProductReview productReview) {
        return new ProductReviewResponse(
                productConverter.toResponse(productReview.getProduct()),
                productReview.getReviews().stream()
                        .map(reviewConverter::toResponse)
                        .collect(Collectors.toList()));
    }
}
