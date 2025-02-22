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

        int initialBalanceFrom = SQLHelper.getCardBalance(fromCard);
        int initialBalanceTo = SQLHelper.getCardBalance(toCard);
        assertNotNull(initialBalanceFrom, "Баланс отправителя не должен быть null");
        assertNotNull(initialBalanceTo, "Баланс получателя не должен быть null");

        int transferAmount = DataHelper.calculateTransferAmount(initialBalanceFrom);

        transferMoney(token, fromCard, toCard, transferAmount);

        int newBalanceFrom = SQLHelper.getCardBalance(fromCard);
        int newBalanceTo = SQLHelper.getCardBalance(toCard);

        assertEquals(initialBalanceFrom - transferAmount, newBalanceFrom, "Баланс отправителя должен уменьшиться");
        assertEquals(initialBalanceTo + transferAmount, newBalanceTo, "Баланс получателя должен увеличиться");
    }
}