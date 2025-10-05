package com.ibs.rest;

import com.ibs.pojo.ProductPojo;
import com.ibs.utill.ProductFactory;
import com.ibs.utill.Specifications;
import io.qameta.allure.Description;
import io.restassured.filter.session.SessionFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
class SandboxApiTest {

    private SessionFilter session;
    private ProductApi productApi;

    @BeforeEach
    void init() {
        session = new SessionFilter();
        productApi = new ProductApi(session);
    }

    @Test
    @Description("Добавление нового продукта и проверка его наличия в списке")
    void addNewProduct() {
        Specifications.installSpecifications(
                Specifications.requestSpecification("https://qualit.applineselenoid.fvds.ru/api"),
                Specifications.responseSpecification(200)
        );

        ProductPojo product = ProductFactory.randomFruit();

        productApi.addProduct(product);
        List<ProductPojo> productList = productApi.getProductList();

        assertThat(productList)
                .as("Проверка, что добавленный продукт есть в списке")
                .extracting(ProductPojo::getName)
                .contains(product.getName());
    }

    @Test
    @Description("Проверка получения списка продуктов")
    void getProductsList() {
        Specifications.installSpecifications(
                Specifications.requestSpecification("https://qualit.applineselenoid.fvds.ru/api"),
                Specifications.responseSpecification(200)
        );

        List<ProductPojo> productList = productApi.getProductList();

        assertThat(productList)
                .as("Список продуктов не должен быть пустым")
                .isNotEmpty();
    }
}

