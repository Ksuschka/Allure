package ru.netology;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }
    private static Faker faker = new Faker(new Locale("ru"));

    @Step("Ввод наименования города")
    public static String generateCity(String locale) {
        String city = faker.options().option("Брянск", "Красноярск", "Нарьян-Мар", "Рязань");
        return city;
    }


    @Step("Выбор даты")
    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

   @Step("Ввод Фамилии и имени")
    public static String generateName(String locale) {
        String name = faker.name().firstName() + " " + faker.name().lastName();
        return name;
    }

    @Step("Ввод Фамилии и имени с буквой ё")
    public static String generateNameLetterE(String locale) {
        String name = faker.name().fullName().concat("ё");
        return name;
    }

    @Step("Ввод номера мобильного телефона")
    public static String generatePhone(String locale) {
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    @Step("Ввод номера мобильного телефона меньше 11 цифр")
    public static String generateShortPhone(String locale) {
        String phone = faker.numerify("+7#####");
        return phone;
    }

    @Step("Ввод номера мобильного телефона без +7")
    public static String generateFalsePhone(String locale) {
        String phone = faker.numerify("###########");
        return phone;
    }

}
