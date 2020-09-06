package ru.job4j.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(item);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.trace(e.getMessage(), e);
        }
        return item;
    }
    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        Transaction tx = null;
        item.setId(Integer.parseInt(id));
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(item);
            tx.commit();
            result = true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.trace(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Item item = new Item();
            item.setId(Integer.parseInt(id));
            session.delete(item);
            tx.commit();
            result = true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
           LOG.trace(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List items = null;
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            items = session.createQuery("from Item").list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.trace(e.getMessage(),  e);
        }
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        List items = null;
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Item where name = :name");
            query.setParameter("name", key);
            items = query.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.trace(e.getMessage(), e);
        }
        return items;
    }

    @Override
    public Item findById(String id) {
        Item result = null;
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            result = session.get(Item.class, Integer.parseInt(id));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.trace(e.getMessage(), e);
        }
        return result;
    }
}
