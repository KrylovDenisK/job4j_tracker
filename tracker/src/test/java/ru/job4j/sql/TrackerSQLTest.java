package ru.job4j.sql;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.Item;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TrackerSQLTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tracker;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/scripts/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }


    @Test
    public void createItem() throws Exception {
        try (Connection connection = pool.getConnection();
             TrackerSQL tracker = new TrackerSQL(connection)) {
            tracker.add(new Item("name", "desc"));
            Assert.assertEquals(tracker.findByName("name").size(), 1);
        }
    }

    @Test
    public void whenDeleteResultTrue() throws Exception {
        try (Connection connection = pool.getConnection();
             TrackerSQL tracker = new TrackerSQL(connection)) {
            Item addItem = tracker.add(new Item("1", "1"));
            Assert.assertTrue(tracker.delete(String.valueOf(addItem.getId())));
        }
    }
    @Test
    public void whenFindByNameResultTrue() throws Exception {
        try (Connection connection = pool.getConnection();
             TrackerSQL tracker = new TrackerSQL(connection)) {
            Item expected = tracker.add(new Item("newItem", "newItem"));
            List<Item> result = tracker.findByName(expected.getName());
            Assert.assertEquals(result, List.of(expected));
        }
    }
}