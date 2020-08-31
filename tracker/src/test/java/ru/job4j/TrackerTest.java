package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TrackerTest {
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription");
        tracker.add(item);
        assertThat(tracker.findAll().get(0), is(item));
    }
    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription1");
        tracker.add(previous);
        Item next = new Item("test2", "testDescription2");
        next.setId(Integer.parseInt(previous.getId()));
        tracker.replace(previous.getId(), next);
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }
    @Test
    public void whenFindItemByIdThenReturnThisItem() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription1");
        tracker.add(previous);
        Item next = new Item("test2", "testDescription2");
        tracker.add(next);
        assertThat(tracker.findById(next.getId()), is(next));
    }
    @Test
    public void whenDeleteItemByIdThenFinilArrayWithoutIt() {
        Tracker tracker = new Tracker();
        Item one = new Item("test1", "testDescription1");
        tracker.add(one);
        Item two = new Item("test2", "testDescription2");
        tracker.add(two);
        Item three = new Item("test3", "testDescription3");
        tracker.add(three);
        List<Item> expect = new ArrayList<>(Arrays.asList(one, three));
        tracker.delete(two.getId());
        assertThat(tracker.findAll(), is(expect));
    }
    @Test
    public void whenFindItemByNameThenReturnArrayThisItem() {
        Tracker tracker = new Tracker();
        Item one = new Item("test1", "testDescription1");
        tracker.add(one);
        Item two = new Item("test2", "testDescription2");
        tracker.add(two);
        Item three = new Item("test3", "testDescription3");
        tracker.add(three);
        Item four = new Item("test2", "testDescription3");
        tracker.add(four);
        List<Item> result = tracker.findByName("test2");
        List<Item> expect = new ArrayList<>(Arrays.asList(two, four));
        assertThat(result, is(expect));
    }
}