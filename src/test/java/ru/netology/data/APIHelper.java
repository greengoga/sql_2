package ru.netology.data;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class APIHelper {
    private static final String BASE_URL = "http://localhost:9999";

    public static String authenticate(String login, String password) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(new DataHelper.AuthRequest(login, password))
                .when()
                .post(BASE_URL + "/api/auth")
                .then()
                .log().all() // Логируем ответ
                .statusCode(200)
                .extract()
                .response();

        String token = response.path("token");
        if (token == null) {
            throw new IllegalStateException("Ошибка: сервер не вернул токен после авторизации!");
        }

        return token;
    }

    public static String verify(String login, String verificationCode) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(new DataHelper.VerificationRequest(login, verificationCode))
                .when()
                .post(BASE_URL + "/api/auth/verification")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
        return response.path("token");
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

    public static Response transferMoney(String token, String from, String to, int amount) {
        return given()
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
    }
}


//package ru.netology.data;
//
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//
//import static io.restassured.RestAssured.given;
//
//public class APIHelper {
//    private static final String BASE_URL = "http://localhost:9999";
//
//    public static String authenticate(String login, String password) {
//        Response response = given()
//                .contentType(ContentType.JSON)
//                .body(new DataHelper.AuthInfo(login, password))
//                .when()
//                .post(BASE_URL + "/api/auth")
//                .then()
//                .log().all()
//                .statusCode(200)
//                .extract()
//                .response();
//
//        return response.path("token");
//    }
//
//    public static String verify(String verificationCode) {
//        Response response = given()
//                .contentType(ContentType.JSON)
//                .body(new DataHelper.VerificationCode(verificationCode))
//                .when()
//                .post(BASE_URL + "/api/auth/verification")
//                .then()
//                .log().all()
//                .statusCode(200)
//                .extract()
//                .response();
//
//        return response.path("token");
//    }
//
//    public static Response getCards(String token) {
//        return given()
//                .contentType(ContentType.JSON)
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get(BASE_URL + "/api/cards")
//                .then()
//                .statusCode(200)
//                .extract()
//                .response();
//    }
//
//    public static Response transferMoney(String token, String from, String to, int amount) {
//        return given()
//                .contentType(ContentType.JSON)
//                .header("Authorization", "Bearer " + token)
//                .body(new DataHelper.TransferRequest(from, to, amount))
//                .when()
//                .post(BASE_URL + "/api/transfer")
//                .then()
//                .statusCode(200)
//                .extract()
//                .response();
//    }
//}