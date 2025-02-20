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
        authenticate("vasya", "qwerty123");

        String verificationCode = SQLHelper.getVerificationCode("vasya");
        assertNotNull(verificationCode, "Код верификации не должен быть null");

        token = verify(verificationCode);
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
    }
}