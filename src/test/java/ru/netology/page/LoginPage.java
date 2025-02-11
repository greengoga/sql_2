package ru.netology.page;


import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.cssSelector;

public class LoginPage {
    private final SelenideElement loginField = $(cssSelector("[data-test-id=login] input"));
    private final SelenideElement passwordField = $(cssSelector("[data-test-id=password] input"));
    private final SelenideElement loginButton = $(cssSelector("[data-test-id=action-login]"));
    private final SelenideElement errorNotification = $(cssSelector("[data-test-id=error-notification] .notification__content"));

    public void verifyErrorNotification(String expectedText) {
        errorNotification.shouldHave(exactText(expectedText)).shouldBe(visible);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        login(info);
        return new VerificationPage();
    }

    public void login(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }
}