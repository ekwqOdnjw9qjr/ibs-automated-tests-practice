package com.ibs.tests;

import com.ibs.asert.RegistrationAssertions;
import com.ibs.basetestclass.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


 class RegistrationTest extends BaseTest {

    @Test
    @DisplayName("Регистрация с некорректным E-mail адресом")
     void registerWithInvalidEmailTest() {

        RegistrationAssertions assertions = new RegistrationAssertions(registerPage);

        homePage.openPage();

        homePage.openRegisterXd();

        homePage.openRegisterForm();

        registerPage.setFirstNameField("Роман");

        registerPage.setLastNameField("Романов");

        registerPage.setEmailField("examplezxc&asd@gmail.com");

        registerPage.setPasswordField("asdQWE123");

        registerPage.setSubscribeToNews(true);

        registerPage.setPrivacyPolicyCheckbox(true);

        registerPage.clickContinueButton();

        assertions.emailErrorShouldBeDisplayed()
                .emailErrorShouldContain("E-Mail введен неправильно!")
                .formShouldNotBeSubmitted();

    }

    @Test
    @DisplayName("Регистрация с именем на английском языке")
     void registerWithEnglishFirstName() {

        RegistrationAssertions assertions = new RegistrationAssertions(registerPage);

        homePage.openPage();

        homePage.openRegisterXd();

        homePage.openRegisterForm();

        registerPage.setFirstNameField("Roman");

        registerPage.setLastNameField("Романов");

        String uniqueEmail = "test" + System.currentTimeMillis() + "@gmail.com";
        registerPage.setEmailField(uniqueEmail);

        registerPage.setPasswordField("asdQWE123");

        registerPage.setSubscribeToNews(true);

        registerPage.setPrivacyPolicyCheckbox(true);

        registerPage.clickContinueButton();

        assertions.formShouldBeSubmitted()
                .successPageShouldContain("Ваша учетная запись создана!");
    }

    @Test
    @DisplayName("Регистрация с некорректным Паролем")
     void registerWithInvalidPasswordTest() {

        RegistrationAssertions assertions = new RegistrationAssertions(registerPage);

        homePage.openPage();

        homePage.openRegisterXd();

        homePage.openRegisterForm();

        registerPage.setFirstNameField("Роман");

        registerPage.setLastNameField("Романов");

        registerPage.setEmailField("examplezxcasd@gmail.com");

        registerPage.setPasswordField("           ");

        registerPage.setSubscribeToNews(true);

        registerPage.setPrivacyPolicyCheckbox(true);

        registerPage.clickContinueButton();

        assertions.passwordErrorShouldBeDisplayed()
                .passwordErrorShouldContain("В пароле должно быть от 4 до 20 символов!")
                .formShouldNotBeSubmitted();

    }

}
