package com.ibs.basetestclass;

import com.ibs.pages.opencart.HomePage;
import com.ibs.pages.opencart.RegisterPage;
import com.ibs.pages.sandbox.ProductPage;
import com.ibs.pages.sandbox.StartPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;
    protected RegisterPage registerPage;

    protected StartPage startPage;

    protected ProductPage productPage;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        homePage = new HomePage(driver);
        registerPage = new RegisterPage(driver);
        startPage = new StartPage(driver);
        productPage = new ProductPage(driver);
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
