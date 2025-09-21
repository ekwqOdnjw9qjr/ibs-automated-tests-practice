package com.ibs.cucumber.steps;

import com.ibs.pages.sandbox.ProductPage;
import com.ibs.pages.sandbox.StartPage;
import com.ibs.utill.JdbcHelper;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.cucumber.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProductSteps {

    private static final Logger log = LoggerFactory.getLogger(ProductSteps.class);

    private final StartPage startPage = new StartPage();
    private ProductPage productPage;

    private String createdName;
    private String createdType;
    private boolean createdExotic;

    @Дано("открыть главную страницу Песочницы")
    public void openSandboxHome() {
        startPage.openPage();
    }
    @Дано("открыть песочницу")
    public void goToSandbox() {
        startPage.openSandbox();
    }
    @Дано("перейти на страницу Товары")
    public void goToProductsPage() {
        productPage = startPage.goToProducts();
    }

    @Когда("добавить новый товар:")
    public void addNewProduct(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> row = rows.getFirst();

        createdName = row.get("Название");
        createdType = row.get("Тип");
        createdExotic = Boolean.parseBoolean(row.get("Экзотический"));

        productPage.addNewProduct(createdName, createdType, createdExotic);
    }

    @Тогда("товар должен появиться в БД")
    public void verifyProductInDb() {
        assertTrue(JdbcHelper.isFoodPresent(createdName, createdType, createdExotic),
                "Продукт не найден в БД: " + createdName);
        log.info("Продукт найден в БД: {}", createdName);
    }


    @Тогда("удалить товар из БД и проверить его отсутствие")
    public void deleteProductFromDb() {
        if (JdbcHelper.isFoodPresent(createdName, createdType, createdExotic)) {
            JdbcHelper.deleteFoodByName(createdName);
            assertFalse(JdbcHelper.isFoodPresent(createdName, createdType, createdExotic),
                    "Продукт не был удален из БД: " + createdName);
            log.info("Продукт удален из БД: {}", createdName);
        }
    }
}
