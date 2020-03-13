package ru.job4j.actions;

import ru.job4j.ITracker;
import ru.job4j.Item;
import ru.job4j.inputs.Input;

import java.util.function.Consumer;

public class AddItem extends BaseAction {

    public AddItem(int key, String name, Consumer<String> output) {
        super(key, name, output);
    }

    @Override
    public void execute(Input input, ITracker tracker) {
        getOutput().accept("------------ Добавление новой заявки --------------");
        String name = input.ask("Введите имя заявки :");
        String desc = input.ask("Введите описание заявки :");
        Item item = new Item(name, desc);
        tracker.add(item);
        getOutput().accept("------------ Новая заявка с getId : " + item.getId() + "-----------");
    }
}

