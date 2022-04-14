package ru.netology;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }
    private static Faker faker = new Faker(new Locale("ru"));

    public static String generateCity(String locale) {
        String city = faker.options().option("Брянск", "Красноярск", "Нарьян-Мар", "Рязань");
        return city;
    }


    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateName(String locale) {
        String name = faker.name().firstName() + " " + faker.name().lastName();
        return name;
    }

    public static String generateNameLetterE(String locale) {
        String name = faker.name().fullName().concat("ё");
        return name;
    }

    public static String generatePhone(String locale) {
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static String generateShortPhone(String locale) {
        String phone = faker.numerify("+7#####");
        return phone;
    }

    public static String generateFalsePhone(String locale) {
        String phone = faker.numerify("###########");
        return phone;
    }

}
