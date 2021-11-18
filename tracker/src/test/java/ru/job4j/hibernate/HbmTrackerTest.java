package ru.job4j.hibernate;

import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.Item;

import javax.persistence.Table;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HbmTrackerTest {
    private HbmTracker hbmTracker = new HbmTracker(new Configuration()
            .configure("hibernateTest.cfg.xml").buildSessionFactory());

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