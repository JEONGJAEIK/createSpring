package com.createspring.spring.jdbc;

import com.createspring.spring.transaction.TransactionSynchronizationManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 관리 클래스
 */
public class DataSourceTransactionManager {
    private final DataSource dataSource;

    public DataSourceTransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 커넥션 획득 스레드로컬에 커넥션이 있으면 그대로 활용한다.
     */
    public Connection getConnection() throws SQLException {
        Connection con = TransactionSynchronizationManager.getConnection();
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
        TransactionSynchronizationManager.setConnection(con);
    }

    /**
     * 인터셉터 호출용 메서드 커밋
     */
    public void commit() throws SQLException {
        TransactionSynchronizationManager.commit();
    }

    /**
     * 인터셉터 호출용 메서드 롤백
     */
    public void rollback() throws SQLException {
        TransactionSynchronizationManager.rollback();
    }

    /**
     * 인터셉터 호출용 메서드 해제
     */
    public void close() {
        Connection con = TransactionSynchronizationManager.getConnection();
        if (con != null) {
            try {
                con.close();
                TransactionSynchronizationManager.clear();
            } catch (SQLException e) {
                System.out.println("커넥션 해제 예외 발생");
            }
        }
    }
}
