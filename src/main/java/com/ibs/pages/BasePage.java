package com.ibs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * @author Mironov Roman
 * Базовый класс. Содержит общие методы для взаимодействия с WebElement.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    /**
     * Конструктор базового класса страницы.
     *
     * @param driver Экземпляр WebDriver, который будет использоваться для взаимодействия с браузером.
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Находит веб-элемент, предварительно дожидаясь его видимости на странице.
     *
     * @param locator Стратегия поиска элемента.
     * @return Найденный WebElement.
     */
    protected WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Очищает текстовое поле и вводит в него указанный текст.
     *
     * @param locator Локатор текстового поля.
     * @param text Текст для ввода в поле.
     */
    protected void set(By locator, String text) {
        WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Устанавливает состояние чекбокса.
     * Клик происходит только в том случае, если текущее состояние элемента
     * не соответствует требуемому.
     *
     * @param locator Локатор чекбокса.
     * @param state Состояние (true - выбрать, false - снять выбор).
     */
    protected void setCheckbox(By locator, boolean state) {
        WebElement element = findElement(locator);
        if (element.isSelected() != state) {
            element.click();
        }
    }

    /**
     * Выполняет клик по элементу, предварительно дождавшись его появления.
     *
     * @param locator Локатор элемента, по которому нужно кликнуть.
     */
    protected void click(By locator) {
        findElement(locator).click();
    }

    /**
     * Проверяет, отображается ли элемент на странице.
     *
     * @param locator Локатор элемента для проверки.
     * @return {@code true} если элемент видим, иначе {@code false}.
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Получает текстовое содержимое элемента.
     *
     * @param locator Локатор элемента.
     * @return Текст содержащийся в элементе.
     */
    protected String getElementText(By locator) {
        return findElement(locator).getText();
    }
}