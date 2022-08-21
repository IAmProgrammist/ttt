package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }


    @Test
    void shouldSendForm() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Семин Денис");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79109009593");
        driver.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
    }

    @Test
    void shouldSendFormWithoutFieldName() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79109009593");
        driver.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldSendWithoutFieldPhone() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Семин Денис");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldSendFormWithoutClickOnCheckbox() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Семин Денис");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79109009593");
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).getText().trim();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text);
    }

    @Test
    void shouldSendFormWithDoubleSurname() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Мусин-Пушкин Олег");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79109009593");
        driver.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
    }

    @Test
    void shouldSendFormWithNameAndSurnameOnLatin() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Semin Denis");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79109009593");
        driver.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldSendFormWithSpaceInFieldSurnameAndName() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys(" ");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79109009593");
        driver.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldSendFormWithLetterInPhoneNumber() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Мусин-Пушкин Олег");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7910900959a");
        driver.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldSendFormWithMissingNumbers() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Мусин-Пушкин Олег");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7910900");
        driver.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldSendFormWithSpaceInFieldPhoneNumber() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Мусин-Пушкин Олег");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void sendAnEmptyForm() {
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", text);
    }


    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }


}
