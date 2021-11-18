package ru.job4j.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.Item;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HbmTrackerTest {

   // private static HbmTracker hbmTracker = new HbmTracker(HibernateFactory.getInstance().getSessionFactory());
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final HbmTracker hbmTracker = new HbmTracker(sf);

    @Test
    public void whenAddThenResultTrue() {
        Item item = new Item("name", "desc");
        hbmTracker.add(item);
        Item result = hbmTracker.findAll().get(0);
        assertThat(result.getName(), is("name"));
        assertThat(result.getDesc(), is("desc"));
    }

    @Test
    public void findAll() {
        Item item1 = new Item("name1", "desc1");
        Item item2 = new Item("name2", "desc2");
        Item item3 = new Item("name3", "desc3");
        hbmTracker.add(item1);
        hbmTracker.add(item2);
        hbmTracker.add(item3);
        assertThat(hbmTracker.findAll().get(0), is(item1));
        assertThat(hbmTracker.findAll().get(1), is(item2));
        assertThat(hbmTracker.findAll().get(2), is(item3));

    }

    @Test
    public void findByName() {
        Item item1 = new Item("name1", "desc1");
        Item item2 = new Item("name1", "desc2");
        Item item3 = new Item("name1", "desc3");
        hbmTracker.add(item1);
        hbmTracker.add(item2);
        hbmTracker.add(item3);
        assertThat(hbmTracker.findByName("name1").size(), is(3));
    }

    @Test
    public void findById() {
        Item item = new Item("name", "desc");
        hbmTracker.add(item);
        String id = item.getId();
        assertThat(hbmTracker.findById(id), is(item));
    }
}