package ru.job4j.actions;

import ru.job4j.ITracker;
import ru.job4j.Item;
import ru.job4j.inputs.Input;

import java.util.function.Consumer;

public class EditItem extends BaseAction {
    public EditItem(int key, String name, Consumer<String> output) {
        super(key, name, output);
    }
    @Override
    public void execute(Input input, ITracker tracker) {
        getOutput().accept("----------------Замена заявки-------------");
        String name = input.ask("Введите имя новой заявки :");
        String desc = input.ask("Введите описание новой заявки :");
        String id = input.ask("Введите id изменяемой заявки");
        Item item = new Item(name, desc);
        if (tracker.replace(id, item)) {
            getOutput().accept("Операция выполнена!");
        } else {
            getOutput().accept("Операция не выполнена!");
        }
    }
}

