package ru.netology.webselenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {
    private WebDriver driver;


    @BeforeAll

    public static void setUp() {
        WebDriverManager.chromedriver().setup();


    }


    @BeforeEach

    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        this.driver.get("http://localhost:9999/");


    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTest1() {
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Сергей Иванов-Петров");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79999999991");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);


    }


    @Test
    void shouldTestIncorrectName() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Fghjkllllllllll");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79999999991");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = By.cssSelector("[data-test-id=name].input_invalid .input__sub").findElement(driver).getText().trim();
        assertEquals(expected, actual);

    }

    @Test
    void shouldTestEmptyName() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79999999991");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = By.cssSelector("[data-test-id=name].input_invalid .input__sub").findElement(driver).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestIncorrectPhoneNumber() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Сергей Иванов-Петров");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("08654");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = By.cssSelector("[data-test-id=phone].input_invalid .input__sub").findElement(driver).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestEmptyPhoneNumber() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Сергей Иванов-Петров");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = By.cssSelector("[data-test-id=phone].input_invalid .input__sub").findElement(driver).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestCheckBox() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Сергей Иванов-Петров");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79999999991");
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals(true, driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed());
    }
}