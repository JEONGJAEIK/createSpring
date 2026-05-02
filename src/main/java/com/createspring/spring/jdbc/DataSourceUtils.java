package com.createspring.spring.jdbc;

import com.createspring.spring.transaction.TransactionSynchronizationManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DataSource 커넥션 획득 유틸
 * 트랜잭션 진행 중이면 동기화 매니저에 바인드된 커넥션을 반환하고,
 * 그렇지 않으면 DataSource에서 새 커넥션을 발급한다.
 */
public abstract class DataSourceUtils {

    private DataSourceUtils() {
    }

    public static Connection getConnection(DataSource dataSource) throws SQLException {
        Connection con = TransactionSynchronizationManager.getResource();
        if (con != null) {
            return con;
        }
        return dataSource.getConnection();
    }
}
