package ru.job4j.actions;

import ru.job4j.ITracker;
import ru.job4j.Item;
import ru.job4j.inputs.Input;

import java.util.function.Consumer;

public class ShowItems extends BaseAction {
    public ShowItems(int key, String name, Consumer<String> output) {
        super(key, name, output);
    }
    @Override
    public void execute(Input input, ITracker tracker) {
        super.getOutput().accept("-------------- Список заявок---------------");
        for (Item item  : tracker.findAll()) {
            getOutput().accept(item.toString());
        }
    }
}
