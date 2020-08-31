package ru.job4j.sql;

import org.junit.Test;
import ru.job4j.Item;
import ru.job4j.sql.proxy.ConnectionRollback;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

public class TrackerSQLTest {
    private DataSource dataSource = new DataSource();

    @Test
    public void createItem() {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.dataSource.getConnection()))) {
            tracker.add(new Item("name", "desc"));
            assertThat(tracker.findByName("name").size(), is(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenDeleteResultTrue() {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.dataSource.getConnection()))) {
            Item addItem = tracker.add(new Item("1", "1"));
            assertThat(tracker.delete(String.valueOf(addItem.getId())), is(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void whenFindByNameResultTrue() {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.dataSource.getConnection()))) {
            Item expected = tracker.add(new Item("newItem", "newItem"));
            List<Item> result = tracker.findByName(expected.getName());
            assertThat(result, is(List.of(expected)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}