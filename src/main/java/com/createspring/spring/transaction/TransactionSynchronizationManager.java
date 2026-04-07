package com.createspring.spring.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 동기화 매니저
 */
public class TransactionSynchronizationManager {
    public static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    public static Connection getConnection() {
        return connectionHolder.get();
    }

    public static void setConnection(Connection con) {
        connectionHolder.set(con);
    }

    public static void commit() throws SQLException {
        connectionHolder.get().commit();
    }

    public static void rollback() throws SQLException {
        connectionHolder.get().rollback();
    }
}
