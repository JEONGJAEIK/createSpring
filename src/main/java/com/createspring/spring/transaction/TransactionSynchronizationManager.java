package com.createspring.spring.transaction;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 트랜잭션 동기화 매니저
 * 스레드로컬 자원(커넥션, 동기화 콜백) 보관 책임만 담당한다.
 */
public abstract class TransactionSynchronizationManager {

    /**
     * 커넥션 보관함
     */
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    /**
     * 트랜잭션이벤트리스너의 콜백 태스크 보관함
     */
    private static final ThreadLocal<List<Runnable>> synchronizations = new ThreadLocal<>();

    private TransactionSynchronizationManager() {
    }

    public static Connection getResource() {
        return connectionHolder.get();
    }

    public static void bindResource(Connection con) {
        connectionHolder.set(con);
    }

    public static void unbindResource() {
        connectionHolder.remove();
    }

    /**
     * AFTER_COMMIT 콜백을 등록한다.
     */
    public static void registerSynchronization(Runnable callback) {
        if (synchronizations.get() == null) {
            synchronizations.set(new ArrayList<>());
        }
        synchronizations.get().add(callback);
    }

    /**
     * 등록된 콜백을 실행하고 정리한다.
     */
    public static void invokeSynchronizations() {
        List<Runnable> callbacks = synchronizations.get();
        if (callbacks != null) {
            for (Runnable callback : callbacks) {
                callback.run();
            }
            synchronizations.remove();
        }
    }

    /**
     * 등록된 콜백을 실행하지 않고 정리만 한다. (롤백 시)
     */
    public static void clearSynchronizations() {
        synchronizations.remove();
    }
}
