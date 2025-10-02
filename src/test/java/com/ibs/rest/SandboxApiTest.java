package com.ibs.rest;

import com.ibs.pojo.ProductPojo;
import com.ibs.utill.ProductFactory;
import com.ibs.utill.Specifications;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


class SandboxApiTest {
    private SessionFilter sessionFilter;

    @BeforeEach
    public void setup() {
        sessionFilter = new SessionFilter();
    }

    @Test
    void addNewProduct() {
        Specifications.installSpecifications(
                Specifications.requestSpecification("http://localhost:8080/api"),
                Specifications.responseSpecification(200));

        ProductPojo product = ProductFactory.randomFruit();
        given()
                .basePath("/food")
                .filter(sessionFilter)
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post()
                .then()
                .body(emptyOrNullString());

        List<ProductPojo> productList = given()
                .basePath("/food")
                .filter(sessionFilter)
                .when()
                .get()
                .then()
                .extract()
                .body()
                .jsonPath()
                .getList("", ProductPojo.class);

        assertThat(productList)
                .isNotEmpty()
                .extracting(ProductPojo::getName)
                .contains(product.getName());
    }

    @Test
    void getProductsList() {
        Specifications.installSpecifications(
                Specifications.requestSpecification("http://localhost:8080/api"),
                Specifications.responseSpecification(200));

        given()
                .basePath("/food")
                .when()
                .get()
                .then()
                .assertThat()
                .body("", not(empty()));
    }

}
