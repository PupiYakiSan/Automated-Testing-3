package ru.netology;


import com.codeborne.selenide.ElementsCollection;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;

import static com.codeborne.selenide.Selenide.*;


public class AlfaCardTest {

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
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void successSubmitForm() {
        open("http://localhost:9999");

        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        String actual = $("[data-test-id=order-success]").getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void successSubmitFormDash() {
        open("http://localhost:9999");

        $("[data-test-id=name] input").val("Краснова Анна-Мария");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        String actual = $("[data-test-id=order-success]").getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void nameEnglish() {
        open("http://localhost:9999");

        $("[data-test-id=name] input").val("Petrov Ivan");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        ElementsCollection elements = $$(By.className("input__sub"));
        String actual = elements.get(0).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void nameNumber() {
        open("http://localhost:9999");

        $("[data-test-id=name] input").val("Иванов2 Тимофей2");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        ElementsCollection elements = $$(By.className("input__sub"));
        String actual = elements.get(0).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void nameSymbol() {
        open("http://localhost:9999");

        $("[data-test-id=name] input").val("Николаев Андрей%");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        ElementsCollection elements = $$(By.className("input__sub"));
        String actual = elements.get(0).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void phoneStartEight () {
        open("http://localhost:9999");

        $("[data-test-id=name] input").val("Ирина Маслова");
        $("[data-test-id=phone] input").val("89001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        ElementsCollection elements = $$(By.className("input__sub"));
        String actual = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void phoneSmallNumber () {
        open("http://localhost:9999");

        $("[data-test-id=name] input").val("Ирина Маслова");
        $("[data-test-id=phone] input").val("+7900111223");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        ElementsCollection elements = $$(By.className("input__sub"));
        String actual = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void phoneBigNumber () {
        open("http://localhost:9999");

        $("[data-test-id=name] input").val("Ирина Маслова");
        $("[data-test-id=phone] input").val("+790011122334");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        ElementsCollection elements = $$(By.className("input__sub"));
        String actual = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void phoneWhitespace () {
        open("http://localhost:9999");

        $("[data-test-id=name] input").val("Ирина Маслова");
        $("[data-test-id=phone] input").val("+7 9001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        ElementsCollection elements = $$(By.className("input__sub"));
        String actual = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void phoneSymbol () {
        open("http://localhost:9999");

        $("[data-test-id=name] input").val("Ирина Маслова");
        $("[data-test-id=phone] input").val("+7900111223@");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        ElementsCollection elements = $$(By.className("input__sub"));
        String actual = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void phoneLetter () {
        open("http://localhost:9999");

        $("[data-test-id=name] input").val("Ирина Маслова");
        $("[data-test-id=phone] input").val("+7A00111223");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        ElementsCollection elements = $$(By.className("input__sub"));
        String actual = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void notAgreement() {
        open("http://localhost:9999");

        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $(By.className("button")).click();

        String actual = $(By.className("input_invalid")).getCssValue("color");
        actual = Color.fromString(actual).asHex();
        String expected = "#ff5c5c";
        Assertions.assertEquals(expected, actual);
    }

}