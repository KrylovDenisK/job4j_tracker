package ru.job4j.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Objects;

public class HibernateFactory {
    private SessionFactory sessionFactory;


    public static HibernateFactory getInstance() {
        return HibernateFactoryHolder.INSTANCE;
    }

    private static final class HibernateFactoryHolder {
        private static final HibernateFactory INSTANCE = new HibernateFactory();
    }

    public SessionFactory getSessionFactory() {
        if (Objects.isNull(this.sessionFactory)) {
            buildSessionFactory();
        }
        return sessionFactory;
    }

    private void buildSessionFactory() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml").buildSessionFactory();
    }
}
