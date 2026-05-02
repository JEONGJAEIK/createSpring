package com.createspring.spring.transaction;

import java.sql.Connection;
import java.sql.SQLException;

public interface PlatformTransactionManager {
    Connection getTransaction() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;
}
