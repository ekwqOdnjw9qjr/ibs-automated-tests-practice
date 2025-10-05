package com.ibs.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * @author Mironov Roman
 * Утилитный класс для получения настроек приложения из properties.
 */
public class Config {
    private Config() {
    }
    private static final Properties properties = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in == null) throw new RuntimeException("application.properties не найден в classpath");
            properties.load(in);

            properties.replaceAll((k, v) -> {
                String value = (String) v;
                if (value != null && value.matches("\\$\\{.+}")) {
                    String envVar = value.substring(2, value.length() - 1);
                    String envValue = System.getenv(envVar);
                    if (envValue != null)
                        return envValue;
                }
                return value;
            });

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке конфигурации", e);
        }
    }

    /**
     * Получает значение свойства по ключу.
     *
     * @param key ключ свойства
     * @return значение свойства или null, если ключ не найден
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }

    /**
     * Получает URL основной страницы Opencart.
     */
    public static String getOpencartBaseUrl() {
        return get("opencart.home.url");
    }

    /**
     * Получает URL страницы для входа в панель администрирования Opencart.
     */
    public static String getOpencartAdminUrl() {
        return get("opencart.admin.login.url");
    }

    /**
     * Получает логин пользователя для входа в панель администрирования Opencart.
     */
    public static String getAdminLogin() {
        return get("admin.login");
    }

    /**
     * Получает пароль пользователя для входа в панель администрирования Opencart.
     */
    public static String getAdminPassword() {
        return get("admin.password");
    }

    /**
     * Получает URL песочницы (sandbox).
     */
    public static String getSandboxBaseUrl() {
        return get("shop.base.url");
    }

    /**
     * Получает URL базы данных.
     */
    public static String getDbUrl() {
        return get("db.url");
    }

    /**
     * Получает имя пользователя для подключения к базе данных.
     */
    public static String getDbUser() {
        return get("db.user");
    }

    /**
     * Получает пароль для подключения к базе данных.
     */
    public static String getDbPassword() {
        return get("db.password");
    }

    /**
     * Получает название браузера.
     */
    public static String getBrowser() {
        String browser = System.getProperty("type.browser");
        if (browser != null && !browser.isEmpty()) {
            return browser;
        }
        return get("type.browser");
    }

    /**
     * Получает URL Selenoid.
     */
    public static String getSelenoidUrl() {
        return get("selenoid.url");
    }

    /**
     * Получает тип драйвера.
     */
    public static String getDriverType() {
        String driver = System.getProperty("type.driver");
        if (driver != null && !driver.isEmpty()) {
            return driver;
        }
        return get("type.driver");
    }

}