package ru.netology;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;


public class AlfaCardTest {

    @BeforeEach
    public void openLocalhost() {
        open("http://localhost:9999");
    }

    @Test
    void successSubmitForm() {

        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void successSubmitFormDash() {

        $("[data-test-id=name] input").val("Краснова Анна-Мария");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void nameEnglish() {

        $("[data-test-id=name] input").val("Petrov Ivan");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void nameNumber() {

        $("[data-test-id=name] input").val("Иванов2 Тимофей2");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void nameSymbol() {

        $("[data-test-id=name] input").val("Николаев Андрей%");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void nameEmpty() {

        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void phoneStartEight () {

        $("[data-test-id=name] input").val("Ирина Маслова");
        $("[data-test-id=phone] input").val("89001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneSmallNumber () {

        $("[data-test-id=name] input").val("Ирина Маслова");
        $("[data-test-id=phone] input").val("+7900111223");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneBigNumber () {

        $("[data-test-id=name] input").val("Ирина Маслова");
        $("[data-test-id=phone] input").val("+790011122334");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneWhitespace () {

        $("[data-test-id=name] input").val("Ирина Маслова");
        $("[data-test-id=phone] input").val("+7 9001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneSymbol () {

        $("[data-test-id=name] input").val("Ирина Маслова");
        $("[data-test-id=phone] input").val("+7900111223@");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneLetter () {

        $("[data-test-id=name] input").val("Ирина Маслова");
        $("[data-test-id=phone] input").val("+7A00111223");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneEmpty () {

        $("[data-test-id=name] input").val("Ирина Маслова");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void notAgreement() {

        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $(By.className("button")).click();

        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldBe(Condition.visible);

    }

}