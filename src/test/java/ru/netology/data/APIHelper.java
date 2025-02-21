package ru.netology.data;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class APIHelper {
    private static final String BASE_URL = "http://localhost:9999";
    public static void authenticate(String login, String password) {
        given()
                .contentType(ContentType.JSON)
                .body(new DataHelper.AuthRequest(login, password))
                .when()
                .post(BASE_URL + "/api/auth")
                .then()
                .log().all()
                .statusCode(200);
    }

    public static String verify(String verificationCode) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(new DataHelper.VerificationRequest("vasya", verificationCode))
                .when()
                .post(BASE_URL + "/api/auth/verification")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        String token = response.path("token");
        if (token == null) {
            throw new IllegalStateException("Ошибка: сервер не вернул токен после верификации!");
        }

        return token;
    }

    public static Response getCards(String token) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(BASE_URL + "/api/cards")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    public static int getCardBalance(String token, String cardNumber) {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(BASE_URL + "/api/cards")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        return response.path("find { it.number == '" + cardNumber + "' }.balance");
    }

    public static int transferMoney(String token, String from, String to, int amount) {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(new DataHelper.TransferRequest(from, to, amount))
                .when()
                .post(BASE_URL + "/api/transfer")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        return response.path("new_balance");
    }
}