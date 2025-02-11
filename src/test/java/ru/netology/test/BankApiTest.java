package ru.netology.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class BankApiTest {

    private static String token;

    @BeforeAll
    static void setUp() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    @DisplayName("Успешная авторизация")
    void shouldAuthenticateSuccessfully() {
        given()
                .baseUri("http://localhost:9999")
                .contentType("application/json")
                .body("{ \"login\": \"vasya\", \"password\": \"qwerty123\" }")
                .when()
                .post("/api/auth")
                .then()
                .statusCode(200);
    }
    @Test
    @DisplayName("Успешная верификация")
    void shouldVerifySuccessfully() {
        given()
                .baseUri("http://localhost:9999")
                .contentType("application/json")
                .body("{ \"login\": \"vasya\", \"code\": \"599640\" }")
                .when()
                .post("/api/auth/verification")
                .then()
                .statusCode(200);
    }
}