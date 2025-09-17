package com.ibs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utill.WaitStrategy;

import java.time.Duration;


/**
 * @author Mironov Roman
 * Базовый класс для всех страниц приложения. Содержит общие методы для взаимодействия
 * с WebElement и управления синхронизацией через WebDriverWait.
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    /**
     * Конструктор базового класса страницы.
     *
     * @param driver Экземпляр WebDriver для взаимодействия с браузером.
     */
    public BasePage(WebDriver driver) {
        this(driver, Duration.ofSeconds(10));
    }

    /**
     * Конструктор с настраиваемым таймаутом ожидания.
     *
     * @param driver Экземпляр WebDriver.
     */
    public BasePage(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }

    /**
     * Выполняет поиск веб-элемента с заданной стратегией ожидания.
     * <p>
     * В зависимости от выбранного {@link WaitStrategy}, метод дожидается:
     * <ul>
     *     <li>{@code PRESENCE}  – появления элемента в DOM (элемент может быть невидимым);</li>
     *     <li>{@code VISIBLE}   – отображения элемента на странице (элемент присутствует и виден);</li>
     *     <li>{@code CLICKABLE} – готовности элемента к клику (элемент виден и доступен для взаимодействия).</li>
     * </ul>
     *
     * @param locator  локатор элемента, который нужно найти.
     * @param strategy стратегия ожидания элемента (presence, visible или clickable).
     * @return найденный {@link WebElement}, соответствующий условиям ожидания.
     * @throws TimeoutException если элемент не удовлетворил условиям ожидания в течение заданного таймаута.
     */
    protected WebElement findElement(By locator, WaitStrategy strategy) {
        return switch (strategy) {
            case PRESENCE  -> wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            case CLICKABLE -> wait.until(ExpectedConditions.elementToBeClickable(locator));
            case VISIBLE   -> wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        };
    }

    /**
     * Находит видимый веб-элемент.
     *
     * @param locator Локатор элемента.
     * @return Найденный WebElement.
     */
    protected WebElement findVisibleElement(By locator) {
        return findElement(locator, WaitStrategy.VISIBLE);
    }

    /**
     * Находит кликабельный веб-элемент.
     *
     * @param locator Локатор элемента.
     * @return Найденный WebElement.
     */
    protected WebElement findClickableElement(By locator) {
        return findElement(locator, WaitStrategy.CLICKABLE);
    }

    /**
     * Находит элемент, присутствующий в DOM.
     *
     * @param locator Локатор элемента.
     * @return Найденный WebElement.
     */
    protected WebElement findPresentElement(By locator) {
        return findElement(locator, WaitStrategy.PRESENCE);
    }


    /**
     * Очищает текстовое поле и вводит в него указанный текст.
     *
     * @param locator Локатор текстового поля.
     * @param text    Текст для ввода в поле.
     * @throws IllegalStateException если элемент не доступен для ввода.
     */
    protected void set(By locator, String text) {
        WebElement element = findVisibleElement(locator);
        if (!element.isEnabled()) {
            throw new IllegalStateException("Element is not enabled: " + locator);
        }
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Устанавливает состояние чекбокса или радиокнопки.
     * Клик происходит только если текущее состояние элемента не соответствует требуемому.
     *
     * @param locator Локатор чекбокса или радиокнопки.
     * @param state   Состояние (true - выбрать, false - снять выбор).
     */
    protected void setCheckbox(By locator, boolean state) {
        WebElement element = findClickableElement(locator);
        if (element.isSelected() != state) {
            element.click();
        }
    }

    /**
     * Выполняет клик по элементу.
     *
     * @param locator Локатор элемента, по которому нужно кликнуть.
     */
    protected void click(By locator) {
        findClickableElement(locator).click();
    }

    /**
     * Проверяет, отображается ли элемент на странице.
     *
     * @param locator Локатор элемента для проверки.
     * @return {@code true} если элемент присутствует и видим, иначе {@code false}.
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return findVisibleElement(locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Получает текстовое содержимое элемента.
     *
     * @param locator Локатор элемента.
     * @return Текст содержащийся в элементе, или пустая строка если элемент не найден.
     */
    protected String getElementText(By locator) {
        try {
            return findVisibleElement(locator).getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    /**
     * Возвращает текущий URL страницы.
     *
     * @return Текущий URL.
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

}