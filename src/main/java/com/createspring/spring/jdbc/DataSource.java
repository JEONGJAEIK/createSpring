package com.createspring.spring.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataSource {

    Connection getConnection() throws SQLException;

}
