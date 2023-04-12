package com.konkon.onlinestore.product.search.service.application.rest.resource;

import com.konkon.onlinestore.product.search.service.application.rest.model.request.CreateProductRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.request.UpdateProductRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.response.ProductResponse;
import com.konkon.onlinestore.product.search.service.config.PostgresResource;
import com.konkon.onlinestore.product.search.service.utils.ValueGenerator;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
class ProductResourceTest {

    @Test
    void Search_Product_Success() {
        UUID productId = UUID.fromString("d9e4c6de-4e3f-4a57-8aaf-06e54c6c45e1");
        given()
                .when()
                .get("/products/" + productId)
                .then()
                .statusCode(200);
    }

    @Test
    void Search_Products_Success() {
        var actual = given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        assertThat(actual, not(Collections.EMPTY_LIST));
    }

    @Test
    void Create_Product_Success() {
        CreateProductRequest request = new CreateProductRequest(
                ValueGenerator.generateName(),
                BigDecimal.valueOf(ValueGenerator.generateInteger(100000000, false)),
                ValueGenerator.generateString(100),
                ValueGenerator.generateString(10),
                1
        );

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/products")
                .then()
                .statusCode(201);
    }

    @Test
    void Update_Product_Success() {
        // FIXME: ここリファクタしたい
        CreateProductRequest createRequest = new CreateProductRequest(
                ValueGenerator.generateName(),
                BigDecimal.valueOf(ValueGenerator.generateInteger(100000000, false)),
                ValueGenerator.generateString(100),
                ValueGenerator.generateString(10),
                1
        );
        var exist = given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .extract()
                .body()
                .as(ProductResponse.class);

        UpdateProductRequest request = new UpdateProductRequest(
                "美味しい食品",
                BigDecimal.valueOf(500),
                "この美味しい食品はあなたの食事を豊かにします",
                "https://example.com/food.jpg",
                exist.version(),
                1
        );

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/products/" + exist.productId())
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    void Delete_Product_Success() {
        UUID productId = UUID.fromString("6d302d6b-8c63-42f9-a9f0-56aebc32b8f6");

        given()
                .when()
                .delete("/products/" + productId)
                .then()
                .statusCode(204);
    }

}