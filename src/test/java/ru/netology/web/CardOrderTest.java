package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.By.cssSelector;

class ApplicationFormTest {
    private SelenideElement form;

    @BeforeEach
    void setup(){
        open("http://localhost:9999");
        form = $("[action]");
    }

    @Nested
    public class PositiveTestCases {
        @Test
        void shouldSubmitRequestIfDataFullyValid() {
            form.$(cssSelector("[type='text']")).sendKeys("Анов Артем");
            form.$(cssSelector("[type='tel']")).sendKeys("+79333333333");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
        }

        @Test
        void shouldSubmitRequestIfSurnameWithHyphen() {
            form.$(cssSelector("[type='text']")).sendKeys("Анов Артем-Росин");
            form.$(cssSelector("[type='tel']")).sendKeys("+79333333333");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
        }
    }

    @Nested
    public class NegativeTestCases {
        @Test
        void shouldNotSubmitIfFormIsEmpty() {
            form.$(cssSelector("[type='button']")).click();
            $(".input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
        }

        @Test
        void shouldNotSubmitIfNameIsEmpty() {
            form.$(cssSelector("[type='text']")).sendKeys("");
            form.$(cssSelector("[type='tel']")).sendKeys("+79333333333");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
        }

        @Test
        void shouldNotSubmitIfPhoneIsEmpty() {
            form.$(cssSelector("[data-test-id=name] input")).sendKeys("Анов Артем");
            form.$(cssSelector("[data-test-id=phone] input")).sendKeys("");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
        }

        @Test
        void shouldNotSubmitIfAgreementIsEmpty() {
            form.$(cssSelector("[data-test-id=name] input")).sendKeys("Анов Артем");
            form.$(cssSelector("[data-test-id=phone] input")).sendKeys("+79333333333");
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(Condition.text("Я соглашаюсь с условиями обработки"));
        }

        @Test
        void shouldNotSubmitRequestIfNameAndSurnameWithUppercase() {
            form.$(cssSelector("[type='text']")).sendKeys("АНОВ АРТЕМ");
            form.$(cssSelector("[type='tel']")).sendKeys("+79333333333");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        }

        @Test
        void shouldNotSubmitRequestIfNameAndSurnameWithLowercase() {
            form.$(cssSelector("[type='text']")).sendKeys("анов артем");
            form.$(cssSelector("[type='tel']")).sendKeys("+79333333333");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        }

        @Test
        void shouldNotSubmitRequestIfOnlyName() {
            form.$(cssSelector("[type='text']")).sendKeys("Артем");
            form.$(cssSelector("[type='tel']")).sendKeys("+79333333333");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
        }

        @Test
        void shouldNotSubmitRequestIfNameAndSurname30Letters() {
            form.$(cssSelector("[type='text']")).sendKeys("Фффффффффффффффффффффффффффффф Аааааааааааааааааааааааааааааа");
            form.$(cssSelector("[type='tel']")).sendKeys("+79333333333");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        }

        @Test
        void shouldNotSubmitRequestIfNameAndSurnameInLatin() {
            form.$(cssSelector("[type='text']")).sendKeys("Anov Artem");
            form.$(cssSelector("[type='tel']")).sendKeys("+79333333333");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        }

        @Test
        void shouldNotSubmitRequestIfNameAndSurnameContainsInvalidSymbols() {
            form.$(cssSelector("[type='text']")).sendKeys("@#@$");
            form.$(cssSelector("[type='tel']")).sendKeys("+79333333333");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        }

        @Test
        void shouldNotSubmitRequestIfPhoneIs1Number() {
            form.$(cssSelector("[type='text']")).sendKeys("Анов Артем");
            form.$(cssSelector("[type='tel']")).sendKeys("+7");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        }

        @Test
        void shouldNotSubmitRequestIfPhoneIs10Numbers() {
            form.$(cssSelector("[type='text']")).sendKeys("Анов Артем");
            form.$(cssSelector("[type='tel']")).sendKeys("+7933333333");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        }

        @Test
        void shouldNotSubmitRequestIfPhoneWithoutPlus() {
            form.$(cssSelector("[type='text']")).sendKeys("Анов Артем");
            form.$(cssSelector("[type='tel']")).sendKeys("++7933333333");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        }

        @Test
        void shouldNotSubmitRequestIfPhoneIs12Numbers() {
            form.$(cssSelector("[type='text']")).sendKeys("Анов Артем");
            form.$(cssSelector("[type='tel']")).sendKeys("+793333333333");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        }

        @Test
        void shouldNotSubmitRequestIfPhoneIsLetters() {
            form.$(cssSelector("[type='text']")).sendKeys("Анов Артем");
            form.$(cssSelector("[type='tel']")).sendKeys("+апыавыаывп");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        }

        @Test
        void shouldNotSubmitRequestIfPhoneContainsSymbols() {
            form.$(cssSelector("[type='text']")).sendKeys("Анов Артем");
            form.$(cssSelector("[type='tel']")).sendKeys("№%@##$");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(cssSelector("[type='button']")).click();
            $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        }
    }
}
