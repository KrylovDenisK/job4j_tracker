package ru.job4j.sql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class DataSource {
    private Connection connection;

    public boolean init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            this.connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
            try (Statement st = connection.createStatement()) {
                st.execute("CREATE TABLE if not exists items (id serial, name varchar(100), description varchar(100), PRIMARY KEY (id))");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection != null;
    }

    public Connection getConnection() {
        if (connection == null) {
            init();
        }
        return connection;
    }
}
