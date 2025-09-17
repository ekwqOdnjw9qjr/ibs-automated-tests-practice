package com.ibs.tests;

import com.ibs.asserts.ProductAssertions;
import com.ibs.basetestclass.BaseTest;
import com.ibs.utill.JdbcHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.stream.Stream;


class ProductTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(ProductTest.class);
    private String createdFoodName;
    private String createdFoodType;
    private boolean createdFoodExotic;

    static Stream<Arguments> productData() {
        return Stream.of(
                Arguments.of("Test product " + UUID.randomUUID(), "FRUIT",
                        true,"База данных недоступна", "Продукт не найден в БД")
        );
    }

    @ParameterizedTest(name = "food_name={0}")
    @MethodSource("productData")
    @DisplayName("Сценарии добавления продукта")
    void addProductAndCheckDb(String foodName, String foodType,
                              boolean isExotic, String connectionErrorMessage,String errorMessage) {

        ProductAssertions.databaseShouldBeAccessible(connectionErrorMessage);

        createdFoodName = foodName;
        createdFoodType = foodType;
        createdFoodExotic = isExotic;

        startPage.openPage();
        startPage.openSandbox();
        startPage.openProductForm();
        productPage.clickAdd();
        productPage.setNameField(foodName);
        productPage.setTypeField(foodType);
        productPage.exoticCheckbox(isExotic);
        productPage.save();

        ProductAssertions.productDetailsShouldMatch(foodName, foodType, isExotic, errorMessage);
    }

    @AfterEach
    void cleanUp() {
        if (createdFoodName != null) {
            if (JdbcHelper.isFoodPresent(createdFoodName, createdFoodType, createdFoodExotic)) {
                JdbcHelper.deleteFoodByName(createdFoodName);

                ProductAssertions.productShouldNotBeInDatabase(
                        createdFoodName, createdFoodType, createdFoodExotic,
                        "Продукт не был удален из БД: " + createdFoodName
                );

                log.info("Продукт удален после теста: name={}", createdFoodName);
            } else {
                ProductAssertions.productShouldNotBeInDatabase(
                        createdFoodName, createdFoodType, createdFoodExotic,
                        "Продукт для удаления не найден в БД: " + createdFoodName
                );
                log.warn("Продукт для удаления не найден: name={}", createdFoodName);
            }

            createdFoodName = null;
            createdFoodType = null;
            createdFoodExotic = false;
        }
    }

}
