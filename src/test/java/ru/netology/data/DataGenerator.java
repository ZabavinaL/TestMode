package ru.netology.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import com.github.javafaker.Faker;

import java.util.Locale;

import lombok.val;

import static io.restassured.RestAssured.given;


public class DataGenerator {

    private DataGenerator() {
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void registerUser(RegistrationData registration) {

        given()
                .spec(requestSpec)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }


    public static RegistrationData activeUser() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().username();
        String password = faker.internet().password();
        String status = "active";
        RegistrationData activeUser = new RegistrationData(login, password, status);
        registerUser(activeUser);
        return activeUser;
    }

    public static RegistrationData blockedUser() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().username();
        String password = faker.internet().password();
        String status = "blocked";
        RegistrationData blockedUser = new RegistrationData(login, password, status);
        registerUser(blockedUser);
        return blockedUser;
    }

//    public static RegistrationData invalidLogin() {
//        Faker faker = new Faker(new Locale("ru"));
//        String login = faker.name().fullName();
//        String password = faker.internet().password();
//        String status = "active";
//        RegistrationData invalidLogin = new RegistrationData(login, password, status);
//        registerUser(invalidLogin);
//        return invalidLogin;
//
//    }

//    public static String invalidLogin(String status) {
//        Faker faker = new Faker(new Locale("ru"));
//        return faker.name().fullName();
//
//    }

//    public static RegistrationData invalidPassword() {
//        Faker faker = new Faker(new Locale("en"));
//        String login = faker.name().username();
//        String password = "1";
//        String status = "active";
//        RegistrationData invalidLogin = new RegistrationData(login, password, status);
//        registerUser(invalidLogin);
//        return invalidLogin;
//    }

//    public static String invalidPassword(String status) {
//        Faker faker = new Faker(new Locale("ru"));
//        return faker.internet().password();
//
//    }

    public static RegistrationData getInvalidPasswordUser() {

        val faker = new Faker(new Locale("en"));
        val login = faker.name().username();

        registerUser(new RegistrationData(login, faker.internet().password(), "active"));
        return new RegistrationData(login, faker.internet().password(), "active");
    }

    public static RegistrationData getInvalidLoginUser() {

        val faker = new Faker(new Locale("en"));
        val password = faker.internet().password();

        registerUser(new RegistrationData(faker.name().username(), password,"active"));
        return new RegistrationData(faker.name().username(), password,"active");
    }

}
