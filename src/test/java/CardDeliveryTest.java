import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardDeliveryTest {

    public String dataGenerate(int day) {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void testForm() {

        // Configuration.holdBrowserOpen = true; // Чтобы браузер не закрывался

        open("http://localhost:9999"); // Перейти на страницу
        // $ - по CSS. $x - по xPath
        // ГОРОД
        $("[placeholder='Город']").setValue("Калининград");
        // ДАТА
        $("[placeholder='Дата встречи']").click();
        $("[placeholder='Дата встречи']").sendKeys(Keys.CONTROL + "A");
        $("[placeholder='Дата встречи']").sendKeys(BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(dataGenerate(4));
        // ФИ
        $x("//input[@name='name']").setValue("Роза Сябитова");
        // ТЕЛЕФОН
        $x("//input[@name='phone']").setValue("+79009009090");
        // СОГЛАСИЕ
        $("[data-test-id='agreement']").click();
        // ЗАБРОНИРОВАТЬ
        $x("//span[@class='button__text']").click();
        // ОКНО УСПЕХА
        $("div[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("div[data-test-id='notification']").shouldHave(exactText("Успешно! Встреча успешно забронирована на " + dataGenerate(4)));

    }

}
