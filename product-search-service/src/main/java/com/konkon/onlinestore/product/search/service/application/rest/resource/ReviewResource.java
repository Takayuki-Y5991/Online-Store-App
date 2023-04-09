package com.konkon.onlinestore.product.search.service.application.rest.resource;


import com.konkon.onlinestore.product.search.service.application.rest.converter.ReviewConverter;
import com.konkon.onlinestore.product.search.service.application.rest.model.request.CreateReviewRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.request.UpdateReviewRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.response.ReviewResponse;
import com.konkon.onlinestore.product.search.service.domain.usecase.ReviewUseCase;
import com.konkon.onlinestore.product.search.service.utils.annotation.UUIDConstraint;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.resteasy.reactive.ResponseStatus;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    @ResponseStatus(201)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ReviewResponse> createReview(@Valid @RequestBody CreateReviewRequest request) {
        return reviewUseCase.createProduct(reviewConverter.toDomain(request))
                .onItem().transform(reviewConverter::toResponse);
    }

    @DELETE
    @Path("{reviewId}")
    @ResponseStatus(204)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Boolean> deleteReview(@UUIDConstraint @PathParam("reviewId") String reviewId) {
        return reviewUseCase.deleteProduct(UUID.fromString(reviewId));
    }

    @PUT
    @Path("{reviewId}")
    @ResponseStatus(204)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Boolean> updateReview(
            @UUIDConstraint @PathParam("reviewId") String reviewId,
            @Valid @RequestBody UpdateReviewRequest request) {
        return reviewUseCase.updateProduct(
                reviewConverter.toDomain(UUID.fromString(reviewId), request));
    }
}
