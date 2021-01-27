package com.xwq.demo;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqliteDemo {
    public static void main(String[] args) throws SQLException {
        // 创建 DataSource（区别）
        // 获取 Connection
        // 操作

        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite://F:/放数据库的位置/test.db");

        DataSource dataSource = sqLiteDataSource;

        Connection connection = dataSource.getConnection();

//        PreparedStatement s = connection.prepareStatement("CREATE TABLE file_meta (id int)");
//        s.executeUpdate();
//
        PreparedStatement show_tables = connection.prepareStatement("INSERT INTO file_meta (id) VALUES (1), (2)");
        show_tables.executeUpdate();

        PreparedStatement s = connection.prepareStatement("SELECT* FROM file_meta");
        ResultSet resultSet = s.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }

        connection.close();
    }
}