package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.DELETE;

public class AppCardDeliveryTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
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
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(firstMeetingDate);
        $("[name=\"name\"]").setValue(validUser.getName());
        $("[name=\"phone\"]").setValue(validUser.getPhone());
        $(".checkbox__box").click();
        $x("//*[text()=\"Запланировать\"]").click();
        $x("//*[text()=\"Успешно!\"]").should(Condition.visible, Duration.ofSeconds(15));
        $x("//*[text()=\"Встреча успешно запланирована на \"]").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15));


        $x("//*[text()=\"Запланировать\"]").click();
        $x("//*[text()=\"Необходимо подтверждение\"]").should(Condition.visible, Duration.ofSeconds(15));
        $x("//*[text()=\"У вас уже запланирована встреча на другую дату. Перепланировать?\"]").should(Condition.visible, Duration.ofSeconds(15));
        $x("//*[text()=\"Перепланировать\"]").click();
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(secondMeetingDate);
        $x("//*[text()=\"Успешно!\"]").should(Condition.visible, Duration.ofSeconds(15));
        $x("//*[text()=\"Встреча успешно запланирована на \"]").shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(15));

    }
}