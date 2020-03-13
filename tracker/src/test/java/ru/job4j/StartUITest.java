package ru.job4j;

import org.junit.Test;
import ru.job4j.inputs.Input;
import ru.job4j.inputs.StubInput;
import ru.job4j.inputs.ValidateInput;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.function.Consumer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class StartUITest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final Consumer<String> output = new Consumer<>() {
        private final PrintStream stdout = new PrintStream(out);
        @Override
        public void accept(String s) {
            stdout.println(s);
        }
    };

       @Test
        public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
            Tracker tracker = new Tracker();
           Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});
           new StartUI(input, tracker, output).init();
           assertThat(tracker.findAll().get(0).getName(), is("test name"));
        }

            @Test
            public void whenUpdateThenTrackerHasUpdatedValue() {
                Tracker tracker = new Tracker();
                Item item = new Item("test name", "desc");
                tracker.add(item);
                Input input = new StubInput(new String[]{"2", "test replace", "заменили заявку", item.getId(), "6"});
                new StartUI(input, tracker, output).init();
                assertThat(tracker.findAll().get(0).getName(), is("test replace"));
            }

            @Test
            public void whenDeleteItemThenTrackerUpdateValue() {
                Tracker tracker = new Tracker();
                Item firstItem = tracker.add(new Item("name1", "desc1"));
                Item secondItem = tracker.add(new Item("name2", "desc2"));
                Item threeItem = tracker.add(new Item("name3", "desc3"));
                Input input = new StubInput(new String[]{"3", secondItem.getId(), "6"});
                new StartUI(input, tracker, output).init();
                assertThat(tracker.findAll(), is(new ArrayList<>(Arrays.asList(firstItem, threeItem))));
            }

           @Test
            public void whenShowAllItemThenSeeAllItem() {
                Tracker tracker = new Tracker();
                Item firstItem = tracker.add(new Item("name1", "desc1"));
                Item secondItem = tracker.add(new Item("name2", "desc2"));
                Item threeItem = tracker.add(new Item("name3", "desc3"));
                Input input = new StubInput(new String[]{"1", "6"});
                StringJoiner expect = new StringJoiner(System.lineSeparator())
                        .add("0. Add items")
                        .add("1. Show All items")
                        .add("2. Edit item")
                        .add("3. Delete item")
                        .add("4. Find item by Id")
                        .add("5. Find Items By Name")
                        .add("6. Exit Program")
                        .add("-------------- Список заявок---------------")
                        .add("Наменование заявки: name1 ID заявки: " + firstItem.getId())
                        .add("Наменование заявки: name2 ID заявки: " + secondItem.getId())
                        .add("Наменование заявки: name3 ID заявки: " + threeItem.getId())
                        .add("0. Add items")
                        .add("1. Show All items")
                        .add("2. Edit item")
                        .add("3. Delete item")
                        .add("4. Find item by Id")
                        .add("5. Find Items By Name")
                        .add("6. Exit Program")
                        .add("Завершение работы");
                new StartUI(input, tracker, output).init();
                assertThat(out.toString(), is(expect.toString() + System.lineSeparator()));
            }
            @Test
            public void whenFindItemByIdThenShowThisItem() {
                Tracker tracker = new Tracker();
                Item firstItem = tracker.add(new Item("name1", "desc1"));
                Item secondItem = tracker.add(new Item("name2", "desc2"));
                Item threeItem = tracker.add(new Item("name3", "desc3"));
                Input input = new StubInput(new String[]{"4", secondItem.getId(), "6"});
                StringJoiner expect = new StringJoiner(System.lineSeparator())
                        .add("0. Add items")
                        .add("1. Show All items")
                        .add("2. Edit item")
                        .add("3. Delete item")
                        .add("4. Find item by Id")
                        .add("5. Find Items By Name")
                        .add("6. Exit Program")
                        .add("------------ Поиск заявки по ID --------------")
                        .add("Имя заявки: " + secondItem.getName() + " Описание заявки: " + secondItem.getDesc() + "")
                        .add("0. Add items")
                        .add("1. Show All items")
                        .add("2. Edit item")
                        .add("3. Delete item")
                        .add("4. Find item by Id")
                        .add("5. Find Items By Name")
                        .add("6. Exit Program")
                        .add("Завершение работы");
                new StartUI(input, tracker, output).init();
                assertThat(out.toString(), is(expect.toString() + System.lineSeparator()));
            }

    @Test
    public void whenInvalidInput() {
        ValidateInput input = new ValidateInput(
                new StubInput(new String[] {"invalid", "1"}), output);
        input.ask("Enter", new int[] {1});
        assertThat(
                this.out.toString(),
                is(
                        String.format("Необходимо ввести корректное значение%n")
                )
        );
    }


}
