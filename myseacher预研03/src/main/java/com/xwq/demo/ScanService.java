package com.xwq.demo;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xuwenqing
 * Date: 2021-01-26
 * Time: 22:32:37
 */
public class ScanService {

    private static final DataSource dataSource;

    static {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        try {
            String classesPath = GetClassPath.class.getProtectionDomain()
                    .getCodeSource().getLocation().getFile();
            String decode = URLDecoder.decode(classesPath, "UTF-8");
            File classesDir = new File(decode);
            String dbPath = classesDir.getParent() + "/test.db";
            sqLiteDataSource.setUrl("jdbc:sqlite://" + dbPath);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        dataSource = sqLiteDataSource;
    }

    public static void main(String[] args) throws SQLException {
//        createTable();
        scan();
    }


    private static List<File> scanResult = new ArrayList<>();
    private static List<File> queryResult = new ArrayList<>();
    private static void scan() throws SQLException {
        File root = new File("F:\\本地搜索工具准备");
        scanResult.clear();
        scanDir(root);

        queryResult.clear();
        queryDir(root);


        System.out.println(scanResult);
        System.out.println(queryResult);

        // scanResult - queryResult: INSERT
        // queryResult - scanResult: DELETE
    }

    private static void queryDir(File root) throws SQLException {
        String sql = "SELECT name, path FROM file_meta WHERE path LIKE ?";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement s = connection.prepareStatement(sql)) {
                s.setString(1, root.getAbsolutePath() + "%");

                try (ResultSet rs = s.executeQuery()) {
                    while (rs.next()) {
                        String name = rs.getString(1);
                        String path = rs.getString(2);

                        File file = new File(path);
                        queryResult.add(file);
                    }
                }
            }
        }
    }

    private static void scanDir(File file) {
        scanResult.add(file);
        if (!file.isDirectory()) {
            return;
        }
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File child : files) {
            scanDir(child);
        }
    }

    private static void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS file_meta (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR(50) NOT NULL,\n" +
                "    path VARCHAR(1000) NOT NULL,\n" +
                "    is_directory BOOLEAN NOT NULL,\n" +
                "    pinyin VARCHAR(50) NOT NULL,\n" +
                "    pinyin_first VARCHAR(50) NOT NULL,\n" +
                "    size BIGINT NOT NULL,\n" +
                "    last_modified TIMESTAMP NOT NULL\n" +
                ");";
        try(Connection connection = dataSource.getConnection()) {
            try (PreparedStatement s = connection.prepareStatement(sql)){
                s.executeUpdate();
            }
        }

    }
}
