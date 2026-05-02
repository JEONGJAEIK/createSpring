package com.createspring.spring.jdbc;

import com.createspring.spring.transaction.AbstractPlatformTransactionManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DataSource 기반 트랜잭션 매니저
 */
public class DataSourceTransactionManager extends AbstractPlatformTransactionManager {
    private final DataSource dataSource;

    public DataSourceTransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 트랜잭션 시작용 자원 획득. DataSource에서 새 커넥션을 발급한다.
     */
    @Override
    protected Connection doGetTransaction() throws SQLException {
        return dataSource.getConnection();
    }
}
