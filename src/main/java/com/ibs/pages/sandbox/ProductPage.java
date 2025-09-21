package com.ibs.pages.sandbox;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.byText;
/**
 * @author Mironov Roman
 * Страница для работы с товарами в Sandbox.
 */
public class ProductPage {

    private final SelenideElement addButton = $(byText("Добавить"));
    private final SelenideElement nameField = $("#name");
    private final SelenideElement typeDropdown = $("#type");
    private final SelenideElement exoticCheckbox = $("#exotic");
    private final SelenideElement saveButton = $("#save");

    /**
     * Добавляет новый товар с указанными параметрами.
     *
     * @param name     название товара.
     * @param type     тип товара.
     * @param isExotic флаг экзотичности.
     */
    @Step("я добавляю новый товар")
    public void addNewProduct(String name, String type, boolean isExotic) {
        addButton.click();
        nameField.setValue(name);
        typeDropdown.selectOptionByValue(type);
        if (isExotic && !exoticCheckbox.isSelected()) {
            exoticCheckbox.click();
        }
        saveButton.click();
    }
}
