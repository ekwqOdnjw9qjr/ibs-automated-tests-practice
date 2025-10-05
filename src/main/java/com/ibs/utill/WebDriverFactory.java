package com.ibs.utill;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.ibs.config.Config.*;
/**
 * @author Mironov Roman
 * Фабрика для настройки и инициализации WebDriver.
 */
@Slf4j
public class WebDriverFactory {
    /**
     * Настройка общих параметров браузера.
     */
    private static void configureDefaults() {
        Configuration.browserSize = "1920x1080";
        Configuration.screenshots = true;
        Configuration.savePageSource = false;
    }

    /**
     * Основной метод для инициализации WebDriver.
     * Определяет тип драйвера (локальный или удалённый) и браузер
     * @throws IllegalArgumentException если передан неизвестный тип драйвера
     */
    public static void setupDriver() {
        String driverType = getDriverType().toLowerCase();
        String browserType = getBrowser().toLowerCase();

        configureDefaults();

        switch (driverType) {
            case "local" -> initLocalDriver(browserType);
            case "remote" -> initRemoteDriver(browserType);
            default -> throw new IllegalArgumentException("Unsupported driver type: " + driverType);
        }
    }

    /**
     * Инициализация локального WebDriver.
     * @param browser имя браузера ("chrome" или "firefox")
     * @throws IllegalArgumentException если передан неподдерживаемый браузер
     */
    private static void initLocalDriver(String browser) {
        switch (browser) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                Configuration.browser = "chrome";
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                Configuration.browser = "firefox";
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    /**
     * Инициализация удалённого WebDriver через Selenoid.
     * @param browser имя браузера
     * @throws IllegalStateException если URL Selenoid не указан
     */
    private static void initRemoteDriver(String browser) {
        String selenoidUrl = getSelenoidUrl();
        if (selenoidUrl == null || selenoidUrl.isEmpty()) {
            throw new IllegalStateException("Selenoid URL not configured");
        }

        Configuration.remote = selenoidUrl;
        Configuration.browser = browser;
        Configuration.browserCapabilities = getDesiredCapabilities(browser);

        log.info("Remote WebDriver configured: {} at {}", browser, selenoidUrl);
    }

    /**
     * Формирует DesiredCapabilities для Selenoid.
     * @param browser имя браузера
     * @return объект DesiredCapabilities с настройками Selenoid
     */
    private static DesiredCapabilities getDesiredCapabilities(String browser) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("acceptInsecureCerts", true);
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", false,
                "screenResolution", "1920x1080x24"
        ));
        return capabilities;
    }
}
