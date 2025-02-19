package ru.netology.test;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.SQLHelper;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.data.APIHelper.*;


public class BankApiTest {
    private static String token;

    @BeforeEach
    void setUp() {
        String authToken = authenticate("vasya", "qwerty123");
        assertNotNull(authToken, "Токен аутентификации не должен быть null");

        String verificationCode = SQLHelper.getVerificationCode("vasya");
        assertNotNull(verificationCode, "Код верификации не должен быть null");

        token = verify("vasya", verificationCode);
        assertNotNull(token, "Токен верификации не должен быть null");
    }

    @Test
    void shouldGetCards() {
        Response response = getCards(token);
        assertNotNull(response, "Ответ не должен быть null");
    }

    @Test
    void shouldTransferMoney() {
        Response response = transferMoney(token, "5559 0000 0000 0002", "5559 0000 0000 0008", 5000);
        assertNotNull(response, "Ответ на перевод не должен быть null");
        assertEquals("ok", response.path("status"), "Перевод должен быть успешным");

        int balanceFrom = SQLHelper.getCardBalance("5559 0000 0000 0002");
        int balanceTo = SQLHelper.getCardBalance("5559 0000 0000 0008");
        assertTrue(balanceFrom >= 0, "Баланс отправителя не может быть отрицательным");
        assertTrue(balanceTo >= 5000, "Получатель должен получить средства");
    }
}


//package ru.netology.test;
//
//import io.restassured.RestAssured;
//import io.restassured.parsing.Parser;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import static ru.netology.data.APIHelper.*;
//import static ru.netology.data.SQLHelper.getVerificationCode;
//
//public class BankApiTest {
//
//    private static String token;
//
//    @BeforeEach
//    void setUp() {
//        token = authenticate("vasya", "qwerty123");
//        assertNotNull(token, "Токен аутентификации не должен быть null");
//        token = verify(getVerificationCode("vasya"));
//        assertNotNull(token, "Токен верификации не должен быть null");
//    }
//
//    @Test
//    void shouldGetCards() {
//        Response response = getCards(token);
//        assertNotNull(response, "Ответ не должен быть null");
//    }
//
//    @Test
//    void shouldTransferMoney() {
//        Response response = transferMoney(token, "5559 0000 0000 0002", "5559 0000 0000 0008", 5000);
//        assertNotNull(response, "Ответ на перевод не должен быть null");
//        assertEquals(response.path("status"), "ok", "Перевод должен быть успешным");
//    }
//}