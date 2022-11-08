package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static ru.netology.testmode.data.DataGenerator.*;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }


    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=\"login\"] input").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(registeredUser.getPassword());
        $("[data-test-id=\"action-login\"] .button__text").click();
        $("#root").shouldBe(Condition.visible);
        //Личный кабинет
        $("#root").shouldHave(Condition.text("Личный"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id=\"login\"] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id=\"action-login\"] .button__text").click();
        $("[data-test-id=\"error-notification\"]").should(visible, Duration.ofSeconds(20));
        $(".notification__title").shouldHave(Condition.text("Ошибка"));
        $(".notification__content").shouldHave(Condition.text("Ошибка!"));
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id=\"login\"] input").setValue(blockedUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(blockedUser.getPassword());
        $("[data-test-id=\"action-login\"] .button__text").click();
        $("[data-test-id=\"error-notification\"]").should(visible, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(Condition.text("Ошибка"));
        $(".notification__content").shouldHave(Condition.text("Ошибка!"));
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }


    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id=\"login\"] input").setValue(wrongLogin);
        $("[data-test-id=\"password\"] input").setValue(registeredUser.getPassword());
        $("[data-test-id=\"action-login\"] .button__text").click();
        $("[data-test-id=\"error-notification\"]").should(visible, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(Condition.text("Ошибка"));
        $(".notification__content").shouldHave(Condition.text("Ошибка!"));
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }


    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id=\"login\"] input").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(wrongPassword);
        $("[data-test-id=\"action-login\"] .button__text").click();
        $("[data-test-id=\"error-notification\"]").should(visible, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(Condition.text("Ошибка"));
        $(".notification__content").shouldHave(Condition.text("Ошибка!"));
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}
