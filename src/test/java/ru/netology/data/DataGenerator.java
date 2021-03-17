package ru.netology.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import com.github.javafaker.Faker;

import java.util.Locale;

import static io.restassured.RestAssured.given;


public class DataGenerator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void User(RegistrationData registration) {

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
        User(activeUser);
        return activeUser;
    }

    public static RegistrationData blockedUser() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().username();
        String password = faker.internet().password();
        String status = "blocked";
        RegistrationData blockedUser = new RegistrationData(login, password, status);
        User(blockedUser);
        return blockedUser;
    }

    public static RegistrationData invalidLogin() {
        Faker faker = new Faker(new Locale("en"));
        String login = "иван";
        String password = faker.internet().password();
        String status = "active";
        RegistrationData invalidLogin = new RegistrationData(login, password, status);
        User(invalidLogin);
        return invalidLogin;
    }

    public static RegistrationData invalidPassword() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().username();
        String password = "1";
        String status = "active";
        RegistrationData invalidLogin = new RegistrationData(login, password, status);
        User(invalidLogin);
        return invalidLogin;
    }
}
