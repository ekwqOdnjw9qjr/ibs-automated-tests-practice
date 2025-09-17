package com.ibs.pages.sandbox;

import com.ibs.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
/**
 * @author Mironov Roman
 * Page Object, представляющий страницу для создания товаров.
 */
public class ProductPage extends BasePage {


    private By addButton = By.xpath("//button[@type='button' and contains(@class, 'btn-primary') " +
            "and @data-target='#editModal' and text()='Добавить']");
    private By nameField = By.id("name");
    private By typeField = By.id("type");
    private By exoticCheckbox = By.id("exotic");
    private By saveButton = By.id("save");
    /**
     * Конструктор страницы продукта.
     */
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Нажимает кнопку "Добавить продукт".
     */
    public void clickAdd() {
        click(addButton);
    }

    /**
     * Заполняет поле названия продукта.
     *
     * @param name название продукта.
     */
    public void setNameField(String name) {
        set(nameField, name);
    }

    /**
     * Устанавливает тип продукта из выпадающего списка.
     *
     * @param type значение типа продукта.
     */
    public void setTypeField(String type) {
        Select typeSelect = new Select(driver.findElement(typeField));
        typeSelect.selectByValue(type);
    }
    /**
     * Устанавливает чекбокс "Экзотический продукт".
     */
    public void exoticCheckbox(boolean exotic) {
        setCheckbox(exoticCheckbox, exotic);
    }
    /**
     * * Сохраняет продукт, нажимая кнопку "Сохранить".
     */
    public void save() {
        click(saveButton);
    }
}
