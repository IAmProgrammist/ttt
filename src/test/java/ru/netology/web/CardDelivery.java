package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;


public class CardDelivery {
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    String planningDate = generateDate(103);

    @BeforeEach
    void openPage() {
        open("http://localhost:9999/");
    }
    @Test
    void fillForm() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Ижевск");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79085685525");
        $("[data-test-id=agreement] span").click();
        $(By.className("button__text")).click();
        $("[data-test-id=notification]")
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));

    }

    @Test
    void fillFormNoCity() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79085685525");
        $("[data-test-id=agreement] span").click();
        $(By.className("button__text")).click();
        $("[data-test-id=city] .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(15));

    }
    @Test
    void fillFormNoName() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Ижевск");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=phone] input").setValue("+79085685525");
        $("[data-test-id=agreement] span").click();
        $(By.className("button__text")).click();
        $("[data-test-id=name] .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(15));

    }
    @Test
    void fillFormNoPhone() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Ижевск");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=agreement] span").click();
        $(By.className("button__text")).click();
        $("[data-test-id=phone] .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(15));

    }
    @Test
    void fillFormNoAgreement() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Ижевск");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79085685525");
        //$("[data-test-id=agreement] span").click();
        $(By.className("button__text")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text")
                .shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"), Duration.ofSeconds(15));

    }
    @Test
    void fillFormNoValidCity() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Izhevsk");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79085685525");
        $("[data-test-id=agreement] span").click();
        $(By.className("button__text")).click();
        $("[data-test-id=city] .input__sub")
                .shouldHave(exactText("Доставка в выбранный город недоступна"), Duration.ofSeconds(15));
    }

    @Test
    void fillFormNoValidPhone() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Ижевск");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+7908568552");
        $("[data-test-id=agreement] span").click();
        $(By.className("button__text")).click();
        $("[data-test-id=phone] .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(15));
    }
}
