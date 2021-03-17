package ru.netology.tmode;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationData;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Condition.text;

public class TestModeTest {

    @BeforeEach
    void setUp() {
        open("http://0.0.0.0:9999");
    }

    @Test
    void correctActiveUser() {
        RegistrationData registrationData = DataGenerator.activeUser();
        $("[data-test-id = 'login'] input").setValue(registrationData.getLogin());
        $("[data-test-id = 'password'] input").setValue(registrationData.getPassword());
        $("button").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void correctBlockedUser() {
        RegistrationData registrationData = DataGenerator.blockedUser();
        $("[data-test-id = 'login'] input").setValue(registrationData.getLogin());
        $("[data-test-id = 'password'] input").setValue(registrationData.getPassword());
        $("button").click();
        $("[data-test-id=error-notification]")
                .shouldHave(text("Пользователь заблокирован"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void invalidLoginEntered() {
        RegistrationData registrationData = DataGenerator.invalidLogin();
        $("[data-test-id = 'login'] input").setValue("какойтологин");
        $("[data-test-id = 'password'] input").setValue(registrationData.getPassword());
        $("button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldHave(text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void invalidPasswordEntered() {
        RegistrationData registrationData = DataGenerator.invalidPassword();
        $("[data-test-id = 'login'] input").setValue(registrationData.getLogin());
        $("[data-test-id = 'password'] input").setValue("какойтопароль");
        $("button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldHave(text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
