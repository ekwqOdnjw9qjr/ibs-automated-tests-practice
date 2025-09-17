package com.ibs.pages.sandbox;

import com.ibs.config.Config;
import com.ibs.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
/**
 * @author Mironov Roman
 * Page Object, представляющий главную страницу приложения.
 */
public class StartPage extends BasePage {
    private static final String HOME_PAGE_URL = Config.getSandboxBaseUrl();

    public StartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Кнопка "Песочница" для открытия выпадающего меню.
     */
    @FindBy(id = "navbarDropdown")
    private WebElement openSandbox;

    /**
     * Ссылка "Товары" в выпадающем меню песочницы.
     */
    @FindBy(xpath = "//a[@href='/food' and contains(@class, 'dropdown-item')]")
    private WebElement openProductForm;


    /**
     * Открывает главную страницу в браузере.
     */
    public void openPage() {
        driver.get(HOME_PAGE_URL);
    }

    /**
     * Кликает по кнопке "Песочница" для вызова выпадающего меню.
     */
    public void openSandbox() {
        wait.until(ExpectedConditions.elementToBeClickable(openSandbox));
        openSandbox.click();
    }

    /**
     * Кликает по ссылке "Товары" в открытом выпадающем меню.
     */
    public void openProductForm() {
        wait.until(ExpectedConditions.elementToBeClickable(openProductForm));
        openProductForm.click();
    }
}
