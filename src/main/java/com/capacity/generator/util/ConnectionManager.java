package com.capacity.generator.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Author: icl
 * Date:2018/05/26
 * Description:
 * Created by icl on 2018/05/26.
 */
public class ConnectionManager {

    private static final String DB_URL = "jdbc:sqlite:./config/sqlite3.db";

    public static Connection getConnection() throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(DB_URL);
        return conn;
    }
}
