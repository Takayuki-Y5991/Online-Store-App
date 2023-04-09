package com.konkon.onlinestore.product.search.service.application.rest.resource;


import com.konkon.onlinestore.product.search.service.application.rest.converter.ReviewConverter;
import com.konkon.onlinestore.product.search.service.application.rest.model.request.CreateReviewRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.request.UpdateReviewRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.response.ReviewResponse;
import com.konkon.onlinestore.product.search.service.domain.usecase.ReviewUseCase;
import com.konkon.onlinestore.product.search.service.utils.annotation.UUIDConstraint;
import com.oracle.svm.core.annotate.Delete;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/reviews")
public class ReviewResource {

    private final ReviewUseCase reviewUseCase;
    private final ReviewConverter reviewConverter;

    public ReviewResource(ReviewUseCase reviewUseCase, ReviewConverter reviewConverter) {
        this.reviewUseCase = reviewUseCase;
        this.reviewConverter = reviewConverter;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ReviewResponse> createReview(@Valid @RequestBody CreateReviewRequest request) {
        return reviewUseCase.createProduct(reviewConverter.toDomain(request))
                .onItem().transform(reviewConverter::toResponse);
    }

    @Delete
    @Path("{reviewId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Boolean> deleteReview(@UUIDConstraint @QueryParam("reviewId") String reviewId) {
        return reviewUseCase.deleteProduct(UUID.fromString(reviewId));
    }

    @PUT
    @Path("{reviewId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Boolean> updateReview(
            @UUIDConstraint @QueryParam("reviewId") String reviewId,
            @Valid @RequestBody UpdateReviewRequest request) {
        return reviewUseCase.updateProduct(
                reviewConverter.toDomain(UUID.fromString(reviewId), request));
    }
}
