package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/app";
    private static final String DB_USER = "app";
    private static final String DB_PASSWORD = "pass";

    public static String getVerificationCode(String login) {
        String codeSQL = "SELECT code FROM auth_codes WHERE user_id = (SELECT id FROM users WHERE login = ?) ORDER BY created DESC LIMIT 1";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            QueryRunner runner = new QueryRunner();
            return runner.query(conn, codeSQL, new ScalarHandler<>(), login);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка получения кода верификации из БД", e);
        }
    }

    public static int getCardBalance(String cardNumber) {
        String sql = "SELECT balance FROM cards WHERE number = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            return new QueryRunner().query(conn, sql, new ScalarHandler<>(), cardNumber);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении баланса", e);
        }
    }
}

//package ru.netology.data;
//
//import org.apache.commons.dbutils.QueryRunner;
//import org.apache.commons.dbutils.handlers.ScalarHandler;
//import lombok.SneakyThrows;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class SQLHelper {
//    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
//
//    private SQLHelper() {
//    }
//
//    private static Connection getConn() throws SQLException {
//        return DriverManager.getConnection(
//                System.getProperty("db.url"),
//                "app",
//                "pass"
//        );
//    }
//
//    @SneakyThrows
//    public static String getVerificationCode() {
//        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
//        try (var conn = getConn()) {
//            return QUERY_RUNNER.query(conn, codeSQL, new ScalarHandler<>());
//        }
//    }
//
//    @SneakyThrows
//    public static void cleanDatabase() {
//        try (var conn = getConn()) {
//            QUERY_RUNNER.execute(conn, "DELETE FROM auth_codes");
//            QUERY_RUNNER.execute(conn, "DELETE FROM card_transactions");
//            QUERY_RUNNER.execute(conn, "DELETE FROM cards");
//            QUERY_RUNNER.execute(conn, "DELETE FROM users");
//        }
//    }
//
//    @SneakyThrows
//    public static void cleanAuthCodes() {
//        try (var conn = getConn()) {
//            QUERY_RUNNER.execute(conn, "DELETE FROM auth_codes");
//        }
//    }
//}