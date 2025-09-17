package com.ibs.asert;

import com.ibs.pages.RegisterPage;
import utill.ErrorType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mironov Roman
 * Класс для выполнения проверок (ассертов) на странице регистрации.
 */
public class RegistrationAssertions {
    private final RegisterPage registerPage;
    /**
     * Конструктор класса.
     */
    public RegistrationAssertions(RegisterPage registerPage) {
        this.registerPage = registerPage;
    }

    /**
     * Проверяет, что сообщение об ошибке для поля E-mail отображается.
     */
    public void emailErrorShouldBeDisplayed() {
        assertTrue(registerPage.isEmailErrorDisplayed(),
                "Сообщение об ошибке для поля E-mail должно отображаться на странице.");
    }
    /**
     * Проверяет текст сообщения об ошибке для поля E-mail.
     *
     * @param expectedError Ожидаемый текст ошибки.
     */
    public void emailErrorShouldContain(String expectedError) {
        assertEquals(registerPage.getEmailErrorText(), expectedError,
                "Текст сообщения об ошибке для E-mail не соответствует ожидаемому.");
    }
    /**
     * Проверяет, что сообщение об ошибке для поля "Пароль" отображается.
     */
    public void passwordErrorShouldBeDisplayed() {
        assertTrue(registerPage.isPasswordErrorDisplayed(),
                "Сообщение об ошибке для поля 'Пароль' должно отображаться на странице.");
    }

    /**
     * Проверяет текст сообщения об ошибке для поля "Пароль".
     *
     * @param expectedError Ожидаемый текст ошибки.
     */
    public void passwordErrorShouldContain(String expectedError) {
        assertEquals(registerPage.getPasswordErrorText(), expectedError,
                "Текст сообщения об ошибке для пароля не соответствует ожидаемому.");
    }
    /**
     * Проверяет, что отправка формы не удалась и пользователь остался на странице регистрации.
     *
     * @return Текущий экземпляр {@link RegistrationAssertions}.
     */
    public RegistrationAssertions formShouldNotBeSubmitted() {
        assertTrue(registerPage.getCurrentUrl().contains("account/register"),
                "Отправка формы не должна была произойти, пользователь должен был остаться на странице регистрации.");
        return this;
    }
    /**
     * Проверяет, что форма была успешно отправлена и произошел переход на страницу успеха.
     *
     * @return Текущий экземпляр {@link RegistrationAssertions}.
     */
    public RegistrationAssertions formShouldBeSubmitted() {
        assertTrue(registerPage.isOnSuccessPage(),
                "Пользователь должен был быть перенаправлен на страницу успешной регистрации.");
        return this;
    }
    /**
     * Проверяет заголовок на странице успешной регистрации.
     *
     * @param successText Ожидаемый текст заголовка.
     */
    public void successPageShouldContain(String successText) {
        assertEquals(registerPage.getSuccessPageText(), successText,
                "Текст на странице успешной регистрации не соответствует ожидаемому.");
    }

    /**
     * Универсальная проверка текста ошибки по типу поля
     */
    public void errorShouldContain(ErrorType type, String expectedText) {
        switch (type) {
            case EMAIL -> {
                emailErrorShouldBeDisplayed();
                emailErrorShouldContain(expectedText);
            }
            case PASSWORD -> {
                passwordErrorShouldBeDisplayed();
                passwordErrorShouldContain(expectedText);
            }
            default -> fail("Unknown ERROR: " + type);
        }
    }

}
