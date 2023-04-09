package com.konkon.onlinestore.product.search.service.application.rest.resource;

import com.konkon.onlinestore.product.search.service.application.rest.model.request.CreateReviewRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.request.UpdateReviewRequest;
import com.konkon.onlinestore.product.search.service.config.PostgresResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
class ReviewResourceTest {

    @Test
    void Create_Review_Success() {
        UUID productId = UUID.fromString("6d302d6b-8c63-42f9-a9f0-56aebc32b8f6");
        String accountId = "3d3e6f11-32e7-4dd2-8263-a3a8fde49f7b";
        var review = new CreateReviewRequest(
                productId,
                accountId,
                4,
                "再販希望",
                "再販希望商品なちがえなし。"
        );
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        given()
                .contentType(ContentType.JSON)
                .body(review)
                .when()
                .post("/reviews")
                .then()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    void Create_Review_Failure_Null_Value() {
        UUID productId = UUID.fromString("6d302d6b-8c63-42f9-a9f0-56aebc32b8f6");
        String accountId = "3d3e6f11-32e7-4dd2-8263-a3a8fde49f7b";
        var review = new CreateReviewRequest(
                productId,
                accountId,
                4,
                null,
                "再販希望商品なちがえなし。"
        );
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        given()
                .contentType(ContentType.JSON)
                .body(review)
                .when()
                .post("/reviews")
                .then()
                .statusCode(400);
    }

    @Test
    void Create_Review_Failure_Invalid_Rating() {
        UUID productId = UUID.fromString("6d302d6b-8c63-42f9-a9f0-56aebc32b8f6");
        String accountId = "3d3e6f11-32e7-4dd2-8263-a3a8fde49f7b";
        var review = new CreateReviewRequest(
                productId,
                accountId,
                10,
                "再販間違えなし",
                "再販希望商品なちがえなし。"
        );
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        given()
                .contentType(ContentType.JSON)
                .body(review)
                .when()
                .post("/reviews")
                .then()
                .statusCode(400);
    }

    @Test
    void Delete_Review_Success() {
        String reviewId = "d6194e4b-74a0-4b7e-8cfd-3b0f8b47ab12";
        given()
                .when()
                .delete("/reviews/" + reviewId)
                .then()
                .statusCode(204);
    }

    @Test
    void Delete_Review_Failure_Valid_Review_Id() {
        String reviewId = "invalid_uuid";
        given()
                .when()
                .delete("/reviews/" + reviewId)
                .then()
                .statusCode(400);
    }

    @Test
    void Update_Review_Success() {
        UUID productId = UUID.fromString("6d302d6b-8c63-42f9-a9f0-56aebc32b8f6");
        String accountId = "3d3e6f11-32e7-4dd2-8263-a3a8fde49f7b";
        String reviewId = "d6194e4b-74a0-4b7e-8cfd-3b0f8b47abaf";
        var review = new UpdateReviewRequest(
                productId,
                accountId,
                4,
                "再販希望",
                "再販希望商品なちがえなし。",
                LocalDateTime.parse("2023-01-15T15:30:00"),
                LocalDateTime.parse("2023-01-15T15:30:00")
        );
        given()
                .contentType(ContentType.JSON)
                .body(review)
                .when()
                .put("/reviews/" + reviewId)
                .then()
                .statusCode(RestResponse.StatusCode.NO_CONTENT);
    }
}