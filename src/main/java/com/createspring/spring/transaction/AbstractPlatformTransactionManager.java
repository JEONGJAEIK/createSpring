package com.createspring.spring.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 플랫폼 트랜잭션 매니저 추상 클래스
 * 트랜잭션의 공통 흐름을 제공하고,
 * 실제 자원 획득은 서브클래스의 doGetTransaction에 위임한다.
 */
public abstract class AbstractPlatformTransactionManager implements PlatformTransactionManager {

    /**
     * 트랜잭션 시작. 새 자원을 획득하고 자동커밋을 끄고 동기화 매니저에 바인딩한다.
     */
    @Override
    public final Connection getTransaction() throws SQLException {
        Connection con = doGetTransaction();
        con.setAutoCommit(false);
        TransactionSynchronizationManager.bindResource(con);
        return con;
    }

    @Override
    public void commit() throws SQLException {
        TransactionSynchronizationManager.getResource().commit();
        TransactionSynchronizationManager.invokeSynchronizations();
    }

    @Override
    public void rollback() throws SQLException {
        TransactionSynchronizationManager.getResource().rollback();
        TransactionSynchronizationManager.clearSynchronizations();
    }

    /**
     * 인터셉터 호출용 메서드 해제
     */
    public void close() {
        Connection con = TransactionSynchronizationManager.getResource();
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("커넥션 해제 예외 발생");
            }
            TransactionSynchronizationManager.unbindResource();
        }
    }

    /**
     * 서브클래스에서 새 커넥션을 발급한다.
     */
    protected abstract Connection doGetTransaction() throws SQLException;
}
