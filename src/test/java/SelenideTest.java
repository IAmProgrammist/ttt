import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class SelenideTest {

    public String generateDate(int days) {

        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    }

    @Test
    public void test() {

        String planningDate = generateDate(13);

        open("http://localhost:9999");
        $("input[placeholder='Город']").setValue("Владивосток");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("input[name='name']").setValue("Александр Сергеевич");
        $("input[name='phone']").setValue("+79271620864");
        $x("//span[@class='checkbox__box']").click();
        $x("//span[@class='button__text']").click();
        String text = $x("//div[@class='notification__content']").should(visible, Duration.ofSeconds(15)).getText();
        $(".notification__content")

                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))

                .shouldBe(Condition.visible);


    }
}
