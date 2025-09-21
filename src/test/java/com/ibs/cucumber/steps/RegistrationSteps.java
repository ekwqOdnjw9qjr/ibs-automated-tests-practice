package com.ibs.cucumber.steps;

import com.ibs.config.Config;
import com.ibs.pages.opencart.HomePage;
import com.ibs.pages.opencart.RegisterPage;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;

import java.util.Map;

public class RegistrationSteps {
    private HomePage homePage = new HomePage();
    private RegisterPage registerPage;

    @Дано("открыть главную страницу Opencart")
    public void openOpencartHomePage() {
        homePage.openPage(Config.getOpencartBaseUrl());
    }

    @И("перейти на страницу регистрации")
    public void goToRegistrationPage() {
        registerPage = homePage.goToRegistration();
    }

    @Когда("заполнить форму регистрации валидными данными:")
    public void fillRegistrationFormWithValidData(Map<String, String> data) {
        registerPage.fillRegistrationForm(data);
    }

    @И("согласиться получать новости")
    public void agreeToNewsletter() {
        registerPage.agreeToNewCheckbox();
    }

    @И("согласиться с политикой конфиденциальности")
    public void agreeToPrivacyPolicy() {
        registerPage.agreeToPrivacyPolicy();
    }

    @И("нажать на кнопку {string}")
    public void clickButton(String buttonName) {
        registerPage.clickContinue();
    }

    @Тогда("увидеть сообщение об успешной регистрации {string}")
    public void seeSuccessMessage(String message) {
        registerPage.checkSuccessMessage(message);
    }

    @Когда("заполнить поле {string} значением {string}")
    public void fillFieldWithValue(String fieldName, String value) {
        registerPage.fillField(fieldName, value);
    }

    @Тогда("увидеть ошибку {string} под полем {string}")
    public void seeErrorMessageUnderField(String errorMessage, String fieldName) {
        registerPage.checkErrorMessageForField(fieldName, errorMessage);
    }

    @И("остаться на странице регистрации")
    public void stayOnRegistrationPage() {
        registerPage.verifyIsOnRegisterPage();
    }
}
