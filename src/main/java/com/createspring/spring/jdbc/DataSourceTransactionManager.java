package com.createspring.spring.jdbc;

import com.createspring.spring.transaction.AbstractPlatformTransactionManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 관리 클래스
 */
public class DataSourceTransactionManager extends AbstractPlatformTransactionManager {
    private final DataSource dataSource;

    public DataSourceTransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 커넥션 획득 스레드로컬에 커넥션이 있으면 그대로 활용한다.
     */
    @Override
    public Connection getConnection() throws SQLException {
        Connection con = super.getConnection();
        if (con != null) {
            return con;
        }
        return dataSource.getConnection();
    }
}
