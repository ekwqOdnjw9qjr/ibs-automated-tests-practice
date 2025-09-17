package com.ibs.tests;

import com.ibs.asert.RegistrationAssertions;
import com.ibs.basetestclass.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utill.ErrorType;

import java.util.stream.Stream;


class RegistrationTest extends BaseTest {

    static Stream<Arguments> invalidRegistrationData() {
        return Stream.of(
                Arguments.of("Роман", "Романов",
                        "examplezxc&asd@gmail.com",
                        "asdQWE123",
                        ErrorType.EMAIL,
                        "E-Mail введен неправильно!"),
                Arguments.of("Роман", "Романов",
                        "examplezxcasd@gmail.com",
                        "           ",
                        ErrorType.PASSWORD,
                        "В пароле должно быть от 4 до 20 символов!")
        );
    }

    static Stream<Arguments> validRegistrationData() {
        return Stream.of(
                Arguments.of("Roman", "Романов",
                        "test" + System.currentTimeMillis() + "@gmail.com",
                        "asdQWE123","Ваша учетная запись создана!")
        );
    }

    @ParameterizedTest(name = "email={2} | password={3}")
    @MethodSource("invalidRegistrationData")
    @DisplayName("Негативные сценарии регистрации")
    void registerWithInvalidData(String firstName,String lastName,
                                 String email,String password,ErrorType errorType,String expectedErrorMessage) {

        RegistrationAssertions assertions = new RegistrationAssertions(registerPage);

        homePage.openPage();
        homePage.openPersonalInfo();
        homePage.openRegisterForm();

        registerPage.setFirstNameField(firstName);
        registerPage.setLastNameField(lastName);
        registerPage.setEmailField(email);
        registerPage.setPasswordField(password);
        registerPage.setSubscribeToNews(true);
        registerPage.setPrivacyPolicyCheckbox(true);
        registerPage.clickContinueButton();

        assertions.formShouldNotBeSubmitted()
                .errorShouldContain(errorType, expectedErrorMessage);
    }

    @ParameterizedTest(name = "firstname={0}")
    @MethodSource("validRegistrationData")
    @DisplayName("Позитивные сценарии для регистрации")
    void registerWithValidData(String firstName,String lastName,
                                 String email,String password,String successPageContain) {

        RegistrationAssertions assertions = new RegistrationAssertions(registerPage);

        homePage.openPage();
        homePage.openPersonalInfo();
        homePage.openRegisterForm();

        registerPage.setFirstNameField(firstName);
        registerPage.setLastNameField(lastName);
        registerPage.setEmailField(email);
        registerPage.setPasswordField(password);
        registerPage.setSubscribeToNews(true);
        registerPage.setPrivacyPolicyCheckbox(true);
        registerPage.clickContinueButton();

        assertions.formShouldBeSubmitted()
                .successPageShouldContain(successPageContain);

    }

}
