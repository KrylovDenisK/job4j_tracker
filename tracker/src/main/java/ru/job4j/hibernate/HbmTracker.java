package ru.job4j.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.ITracker;
import ru.job4j.Item;

import java.util.List;

public class HbmTracker implements ITracker, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sessionFactory = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private static final Logger LOG = LogManager.getLogger(HbmTracker.class.getName());

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Override
    public Item add(Item item) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.trace(e.getMessage(), e);
        }
        return item;
    }
    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        item.setId(Integer.parseInt(id));
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            LOG.trace(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Item item = new Item();
            item.setId(Integer.parseInt(id));
            session.delete(item);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
           LOG.trace(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List items = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            items = session.createQuery("from Item").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.trace(e.getMessage(),  e);
        }
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        List items = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Item where name = :name");
            query.setParameter("name", key);
            items = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.trace(e.getMessage(), e);
        }
        return items;
    }

    @Override
    public Item findById(String id) {
        Item result = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            result = session.get(Item.class, Integer.parseInt(id));
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.trace(e.getMessage(), e);
        }
        return result;
    }
}
