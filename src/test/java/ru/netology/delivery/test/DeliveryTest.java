package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.conditions.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder=\"Город\"]").setValue(validUser.getCity());
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder=\"Дата встречи\"]").setValue(firstMeetingDate);
        $("[name=\"name\"]").setValue(validUser.getName());
        $("[name=\"phone\"]").setValue(validUser.getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $("button .button__text").click();

        $("[data-test-id=\"success-notification\"] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder=\"Дата встречи\"]").setValue(secondMeetingDate);
        $("button .button__text").click();

        $("[data-test-id=\"replan-notification\"] .notification__content")
                .should(visible, Duration.ofSeconds(15));
        $("button .button__text").click();
        $("[data-test-id=\"success-notification\"] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }
}
