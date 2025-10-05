package com.ibs.rest;

import com.ibs.pojo.ProductPojo;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;

public class ProductApi {

    private final SessionFilter session;

    public ProductApi(SessionFilter session) {
        this.session = session;
    }

    @Step("Добавляем продукт: {product.name}")
    public void addProduct(ProductPojo product) {
        given()
                .basePath("/food")
                .filter(new AllureRestAssured())
                .filter(session)
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post()
                .then()
                .body(emptyOrNullString());
    }

    @Step("Получаем список продуктов")
    public List<ProductPojo> getProductList() {
        return given()
                .basePath("/food")
                .filter(new AllureRestAssured())
                .filter(session)
                .when()
                .get()
                .then()
                .extract()
                .jsonPath()
                .getList("", ProductPojo.class);
    }
}
