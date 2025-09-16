package com.ibs.asert;

import com.ibs.pages.RegisterPage;
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
     *
     * @return Текущий экземпляр {@link RegistrationAssertions} для вызова следующих проверок.
     */
    public RegistrationAssertions emailErrorShouldBeDisplayed() {
        assertTrue(registerPage.isEmailErrorDisplayed(),
                "Сообщение об ошибке для поля E-mail должно отображаться на странице.");
        return this;
    }

    /**
     * Проверяет текст сообщения об ошибке для поля E-mail.
     *
     * @param expectedError Ожидаемый текст ошибки.
     * @return Текущий экземпляр {@link RegistrationAssertions}.
     */
    public RegistrationAssertions emailErrorShouldContain(String expectedError) {
        assertEquals(registerPage.getEmailErrorText(), expectedError,
                "Текст сообщения об ошибке для E-mail не соответствует ожидаемому.");
        return this;
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
     * Проверяет, что сообщение об ошибке для поля "Пароль" отображается.
     *
     * @return Текущий экземпляр {@link RegistrationAssertions}.
     */
    public RegistrationAssertions passwordErrorShouldBeDisplayed() {
        assertTrue(registerPage.isPasswordErrorDisplayed(),
                "Сообщение об ошибке для поля 'Пароль' должно отображаться на странице.");
        return this;
    }

    /**
     * Проверяет текст сообщения об ошибке для поля "Пароль".
     *
     * @param expectedError Ожидаемый текст ошибки.
     * @return Текущий экземпляр {@link RegistrationAssertions}.
     */
    public RegistrationAssertions passwordErrorShouldContain(String expectedError) {
        assertEquals(registerPage.getPasswordErrorText(), expectedError,
                "Текст сообщения об ошибке для пароля не соответствует ожидаемому.");
        return this;
    }
}