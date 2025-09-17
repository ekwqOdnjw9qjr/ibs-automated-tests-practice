package com.ibs.asserts;

import com.ibs.utill.JdbcHelper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Mironov Roman
 * Класс для проверок продуктов в БД.
 */
public class ProductAssertions {


    /**
     * Проверяет, что в базе данных хранятся точные данные продукта.
     *
     * @param name Название продукта.
     * @param type Тип продукта.
     * @param exotic Флаг экзотичности.
     * @param errorMessage Сообщение об ошибке, если данные в базе не совпадают с ожидаемыми.
     */
    public static void productDetailsShouldMatch(String name, String type, boolean exotic, String errorMessage) {
        assertTrue(JdbcHelper.verifyFoodDetails(name, type, exotic), errorMessage);
    }

    /**
     * Проверяет, что продукт с указанными параметрами отсутствует в базе.
     *
     * @param foodName Название продукта.
     * @param foodType Тип продукта.
     * @param isExotic Флаг экзотичности.
     * @param errorMessage Сообщение об ошибке, если продукт найден.
     */
    public static void productShouldNotBeInDatabase(String foodName, String foodType, boolean isExotic, String errorMessage) {
        boolean isPresent = JdbcHelper.isFoodPresent(foodName, foodType, isExotic);
        assertFalse(isPresent, errorMessage);
    }

    /**
     * Проверяет доступность базы данных.
     *
     * @param errorMessage Сообщение об ошибке, если база недоступна.
     */
    public static void databaseShouldBeAccessible(String errorMessage) {
        assertTrue(JdbcHelper.isDatabaseAccessible(), errorMessage);
    }
}
