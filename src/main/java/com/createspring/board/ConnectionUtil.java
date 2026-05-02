package com.createspring.board;

import com.createspring.spring.annotation.Component;
import com.createspring.spring.jdbc.DataSource;

import java.sql.*;

/**
 * 사용자 정의 DataSource 구현체
 */
@Component
public class ConnectionUtil implements DataSource {
    private static final String URL = "jdbc:mysql://localhost:3306/spring?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul";
    private static final String USER = "root";
    private static final String PASSWORD = "456456aa";

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * 커넥션 종료
     */
    public static void close(Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("리졸트 셋 예외 발생");
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("스테이트먼트 예외 발생");
            }
        }
    }
}
