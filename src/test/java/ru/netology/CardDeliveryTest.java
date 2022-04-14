package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.DataGenerator.generateDate;

@Story("Проверка валидация полей")
@Feature("Отправка заявки на доставку карты")

public class CardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void openBrowser() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
    }


    @Test
    @DisplayName("Отправка заявки")
    public void shouldApplicationForm() {

        $("[data-test-id= 'city'] input").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id= 'date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id= 'date'] input").setValue(DataGenerator.generateDate(4));
        $("[data-test-id= 'name'] input").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id= 'phone'] input").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(withText("Запланировать")).click();
        $(".notification_visible")
                .shouldBe(exist, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__title")
                .shouldHave(text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateDate(4)), Duration.ofSeconds(15));

    }

    @Test
    @DisplayName("Перенос даты заявки")
    public void shouldDateRescheduling(){
        $("[data-test-id= 'city'] input").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id= 'date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id= 'date'] input").setValue(DataGenerator.generateDate(4));
        $("[data-test-id= 'name'] input").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id= 'phone'] input").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(withText("Запланировать")).click();
        $(".notification_visible")
                .shouldBe(exist, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__title")
                .shouldHave(text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateDate(4)), Duration.ofSeconds(15));
        $("[data-test-id= 'date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id= 'date'] input").setValue(DataGenerator.generateDate(5));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Перепланировать?"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $$("button").find(Condition.exactText("Перепланировать")).click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateDate(5)), Duration.ofSeconds(15));

    }

    @Test
    @DisplayName("Отправка заявки с буквой Ё в поле Фамилия и имя")
    public void shouldNameLetterE() {
        $("[data-test-id= 'city'] input").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id= 'date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id= 'date'] input").setValue(DataGenerator.generateDate(4));
        $("[data-test-id= 'name'] input").setValue(DataGenerator.generateNameLetterE("ru"));
        $("[data-test-id= 'phone'] input").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(withText("Запланировать")).click();
        $(".notification_visible")
                .shouldBe(exist, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__title")
                .shouldHave(text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateDate(4)), Duration.ofSeconds(15));

    }

    @Test
    @DisplayName("Оправка заявки с указанием номера телефона меньше 11 цифр ")
    public void shouldApplicationFormShortPhone() {
        $("[data-test-id= 'city'] input").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id= 'date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id= 'date'] input").setValue(DataGenerator.generateDate(4));
        $("[data-test-id= 'name'] input").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id= 'phone'] input").setValue(DataGenerator.generateShortPhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    @DisplayName("Отправка заявки с указанием номера телефона без +7")
    public void shouldApplicationFalsePhone() {
        $("[data-test-id= 'city'] input").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id= 'date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id= 'date'] input").setValue(DataGenerator.generateDate(4));
        $("[data-test-id= 'name'] input").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id= 'phone'] input").setValue(DataGenerator.generateFalsePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

}
