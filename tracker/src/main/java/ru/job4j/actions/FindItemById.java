package ru.job4j.actions;

import ru.job4j.ITracker;
import ru.job4j.Item;
import ru.job4j.inputs.Input;

import java.util.function.Consumer;

public class FindItemById extends BaseAction {
    public FindItemById(int key, String name, Consumer<String> output) {
        super(key, name, output);
    }
    @Override
    public void execute(Input input, ITracker tracker) {
        getOutput().accept("------------ Поиск заявки по ID --------------");
        String id = input.ask("Введите ID заявки :");
        Item item = tracker.findById(id);
        if (item != null) {
            getOutput().accept("Имя заявки: " + item.getName() + " Описание заявки: " + item.getDesc());
        } else {
            getOutput().accept("Заявка не найдена!!!");
        }
    }
}

