package ru.job4j.sql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ITracker;
import ru.job4j.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TrackerSQL implements ITracker, AutoCloseable {

    private Connection connection;
    private static final Logger LOGGER = LogManager.getLogger(TrackerSQL.class.getName());


    public TrackerSQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement st = connection.prepareStatement("insert into items (name, description) values (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, item.getName());
            st.setString(2, item.getDesc());
            st.executeUpdate();
            try (ResultSet result = st.getGeneratedKeys()) {
                if (result.next()) {
                    item.setId(String.valueOf(result.getInt(1)));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        try (PreparedStatement st = connection.prepareStatement("update items set name = ?, description = ? where id = ?;")) {
            st.setString(1, item.getName());
            st.setString(2, item.getDesc());
            st.setInt(3, Integer.valueOf(id));
            if (st.executeUpdate() > 0) {
                result = true;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        int result = 0;
        try (PreparedStatement st = connection.prepareStatement("delete from items where id = ?")) {
            st.setInt(1, Integer.valueOf(id));
            result = st.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result == 1;
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement("select id, name, description from items")) {
           ResultSet result = st.executeQuery();
           while (result.next()) {
               items.add(new Item(
                       result.getString("id"),
                       result.getString("name"),
                       result.getString("description")
                       )
               );
           }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement("select * from items where name = ?")) {
            st.setString(1, key);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                items.add(new Item(
                        result.getString("id"),
                        result.getString("name"),
                        result.getString("description"))
                );
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return items;
    }

    @Override
    public Item findById(String id) {
        Item item = null;
        try (PreparedStatement st = connection.prepareStatement("select * from items where id = ?")) {
            st.setInt(1, Integer.valueOf(id));
            ResultSet result = st.executeQuery();
            while (result.next()) {
                item = new Item(
                        result.getString("id"),
                        result.getString("name"),
                        result.getString("description")
                );
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return item;
    }
}
