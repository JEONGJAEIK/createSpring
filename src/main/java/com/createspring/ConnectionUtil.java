package com.createspring;

import java.sql.*;

/**
 * 커넥션 유틸 스레드로컬의 커넥션을 활용한다.
 */
public class ConnectionUtil {
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private static final String URL = "jdbc:mysql://localhost:3306/spring?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul";
    private static final String USER = "root";
    private static final String PASSWORD = "456456aa";

    /**
     * 커넥션 획득 스레드로컬에 커넥션이 있으면 그대로 활용한다.
     */
    public static Connection getConnection() throws SQLException {
        Connection con = connectionHolder.get();
        if (con != null) {
            return con;
        }
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
                System.out.println("결과가 조졌다");
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("쿼리가 조졌다");
            }
        }
    }

    /**
     * 인터셉터 호출용 메서드 트랜잭션 시작
     */
    public static void begin() throws SQLException {
        Connection con = getConnection();
        con.setAutoCommit(false);
        connectionHolder.set(con);
    }

    /**
     * 인터셉터 호출용 메서드 커밋
     */
    public static void commit() throws SQLException {
        connectionHolder.get().commit();
    }

    /**
     * 인터셉터 호출용 메서드 롤백
     */
    public static void rollback() throws SQLException {
        connectionHolder.get().rollback();
    }

    public static void close() {
        Connection con = connectionHolder.get();
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("커넥션이 조졌다");
            }
        }
    }
}
