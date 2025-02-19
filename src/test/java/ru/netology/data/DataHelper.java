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


//package ru.netology.data;
//
//import com.github.javafaker.Faker;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.Value;
//
//import java.util.Locale;
//
//public class DataHelper {
//    private DataHelper() {
//    }
//
//    @Data
//    @AllArgsConstructor
//    public static class AuthInfo {
//        private String login;
//        private String password;
//    }
//
//    @Data
//    @AllArgsConstructor
//    public static class VerificationCode {
//        private String code;
//    }
//
//    @Data
//    @AllArgsConstructor
//    public static class TransferRequest {
//        private String from;
//        private String to;
//        private int amount;
//    }
//}