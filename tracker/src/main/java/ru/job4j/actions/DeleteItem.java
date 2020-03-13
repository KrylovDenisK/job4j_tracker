package ru.job4j.actions;

import ru.job4j.ITracker;
import ru.job4j.inputs.Input;

import java.util.function.Consumer;

public class DeleteItem extends BaseAction {

    public DeleteItem(int key, String name, Consumer<String> output) {
        super(key, name, output);
    }
    @Override
    public void execute(Input input, ITracker tracker) {
        super.getOutput().accept("--------------Удаление заявки-------------");
        String id = input.ask("Введите id заявки:");
        if (tracker.delete(id)) {
           getOutput().accept("Операция выполнена!");
        } else {
            getOutput().accept("Операция не выполнена!");
        }
    }
}

