package ru.netology.test;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
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
        String fromCard = DataHelper.getFirstCardNumber();
        String toCard = DataHelper.getSecondCardNumber();

        int fromKopecksBefore = SQLHelper.getCardBalance(fromCard);
        int toKopecksBefore = SQLHelper.getCardBalance(toCard);

        int fromRubBefore = fromKopecksBefore / 100;  // например, 10_000 руб., если 1_000_000 копеек
        int toRubBefore = toKopecksBefore / 100;

        int transferRub = fromRubBefore / 2; // половина баланса отправителя

        transferMoney(token, fromCard, toCard, transferRub);

        int fromKopecksAfter = SQLHelper.getCardBalance(fromCard);
        int toKopecksAfter = SQLHelper.getCardBalance(toCard);

        int fromRubAfter = fromKopecksAfter / 100;
        int toRubAfter = toKopecksAfter / 100;

        assertEquals(fromRubBefore - transferRub, fromRubAfter,
                "Баланс отправителя должен уменьшиться на сумму перевода в рублях");
        assertEquals(toRubBefore + transferRub, toRubAfter,
                "Баланс получателя должен увеличиться на сумму перевода в рублях");
    }
}