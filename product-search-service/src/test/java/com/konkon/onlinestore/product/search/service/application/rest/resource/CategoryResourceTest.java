package com.konkon.onlinestore.product.search.service.application.rest.resource;

import com.konkon.onlinestore.product.search.service.config.PostgresResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
class CategoryResourceTest {

    @Test
    void Search_Products_With_Category_Success() {
        Integer categoryId = 1;
        given()
                .when()
                .get("/categories/" + categoryId)
                .then()
                .statusCode(200);
    }

    @Test
    void Create_Category_Success() {
        String name = "TestCategory";
        given()
                .queryParam("name", name)
                .when()
                .post("/categories")
                .then()
                .statusCode(201);
    }

    @Test
    void Create_Category_Failure_BlankName() {
        String name = "";
        given()
                .queryParam("name", name)
                .when()
                .post("/categories")
                .then()
                .statusCode(400);
    }

    @Test
    void Search_Categories_Success() {
        given()
                .when()
                .get("/categories")
                .then()
                .statusCode(200);
    }
}