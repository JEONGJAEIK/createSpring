package com.createspring.spring.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceTransactionManager {
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private final DataSource dataSource;

    public DataSourceTransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 커넥션 획득 스레드로컬에 커넥션이 있으면 그대로 활용한다.
     */
    public Connection getConnection() throws SQLException {
        Connection con = connectionHolder.get();
        if (con != null) {
            return con;
        }
        return dataSource.getConnection();
    }

    /**
     * 인터셉터 호출용 메서드 트랜잭션 시작
     */
    public void begin() throws SQLException {
        Connection con = getConnection();
        con.setAutoCommit(false);
        connectionHolder.set(con);
    }

    /**
     * 인터셉터 호출용 메서드 커밋
     */
    public void commit() throws SQLException {
        connectionHolder.get().commit();
    }

    /**
     * 인터셉터 호출용 메서드 롤백
     */
    public void rollback() throws SQLException {
        connectionHolder.get().rollback();
    }

    /**
     * 인터셉터 호출용 메서드 해제
     */
    public void close() {
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
