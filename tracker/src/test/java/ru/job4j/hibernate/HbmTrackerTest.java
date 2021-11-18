package ru.job4j.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.Item;

import static org.hamcrest.CoreMatchers.is;


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
        Assert.assertEquals(result.getName(), "name");
        Assert.assertEquals(result.getDesc(), "desc");
    }

    @Test
    public void findAll() {
        Item item1 = new Item("name1", "desc1");
        Item item2 = new Item("name2", "desc2");
        Item item3 = new Item("name3", "desc3");
        hbmTracker.add(item1);
        hbmTracker.add(item2);
        hbmTracker.add(item3);
        Assert.assertEquals(hbmTracker.findAll().get(0), item1);
        Assert.assertEquals(hbmTracker.findAll().get(1), item2);
        Assert.assertEquals(hbmTracker.findAll().get(2), item3);

    }

    @Test
    public void findByName() {
        Item item1 = new Item("name1", "desc1");
        Item item2 = new Item("name1", "desc2");
        Item item3 = new Item("name1", "desc3");
        hbmTracker.add(item1);
        hbmTracker.add(item2);
        hbmTracker.add(item3);
        Assert.assertEquals(hbmTracker.findByName("name1").size(), 3);
    }

    @Test
    public void findById() {
        Item item = new Item("name", "desc");
        hbmTracker.add(item);
        String id = item.getId();
        Assert.assertEquals(hbmTracker.findById(id), item);
    }
}