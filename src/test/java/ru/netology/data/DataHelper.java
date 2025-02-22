package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;

public class DataHelper {
    private DataHelper() {
    }


    public static String getFirstCardNumber() {
        return "5559 0000 0000 0002";
    }

    public static String getSecondCardNumber() {
        return "5559 0000 0000 0001";
    }

    public static int calculateTransferAmount(int balance) {
        return balance / 2;
    }

    @Data
    @AllArgsConstructor
    public static class AuthRequest {
        private String login;
        private String password;
    }

    @Data
    @AllArgsConstructor
    public static class VerificationRequest {
        private String login;
        private String code;
    }

    @Data
    @AllArgsConstructor
    public static class TransferRequest {
        private String from;
        private String to;
        private int amount;
    }
}