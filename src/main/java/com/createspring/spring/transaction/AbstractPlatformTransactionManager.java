package com.createspring.spring.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 트랜잭션 동기화 매니저
 */
public abstract class AbstractPlatformTransactionManager implements PlatformTransactionManager{

    /**
     * 커넥션 보관함
     */
    public static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    /**
     * 트랜잭션이벤트리스너의 콜백 태스크 보관함
     */
    private static final ThreadLocal<List<Runnable>> eventHolder = new ThreadLocal<>();

    public Connection getConnection() throws SQLException{
        return connectionHolder.get();
    }

    public void setConnection(Connection con) {
        connectionHolder.set(con);
    }

    /**
     * 인터셉터 호출용 메서드 트랜잭션 시작
     */
    @Override
    public Connection getTransaction() throws SQLException {
        Connection con = getConnection();
        con.setAutoCommit(false);
        setConnection(con);
        return con;
    }

    @Override
    public void commit() throws SQLException {
        connectionHolder.get().commit();
        invokeSynchronizations();
    }

    @Override
    public void rollback() throws SQLException {
        connectionHolder.get().rollback();
        eventHolder.remove();
    }


    /**
     * 인터셉터 호출용 메서드 해제
     */
    public void close() throws SQLException {
        Connection con = getConnection();
        if (con != null) {
            try {
                con.close();
                clear();
            } catch (SQLException e) {
                System.out.println("커넥션 해제 예외 발생");
            }
        }
    }

    private void clear() {
        connectionHolder.remove();
    }


    /**
     * AFTER_COMMIT 콜백을 등록한다.
     */
    public static void registerSynchronization(Runnable callback) {
        if (eventHolder.get() == null) {
            eventHolder.set(new ArrayList<>());
        }
        eventHolder.get().add(callback);
    }

    /**
     * 등록된 콜백을 실행하고 정리한다.
     */
    private static void invokeSynchronizations() {
        List<Runnable> callbacks = eventHolder.get();
        if (callbacks != null) {
            for (Runnable callback : callbacks) {
                callback.run();
            }
            eventHolder.remove();
        }
    }
}
