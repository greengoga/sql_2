package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;

public class DataHelper {
    private DataHelper() {
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