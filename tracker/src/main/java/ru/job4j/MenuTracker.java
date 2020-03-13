package ru.job4j;

import ru.job4j.actions.*;
import ru.job4j.inputs.Input;

import java.util.ArrayList;
import java.util.function.Consumer;

public class MenuTracker {
    private Input input;
    private ITracker tracker;
    private final Consumer<String> output;
    private ArrayList<UserAction> actions = new ArrayList<>();


    public MenuTracker(Input input, ITracker tracker, Consumer<String> output) {
        this.input = input;
        this.tracker = tracker;
        this.output = output;
    }
    /**
     * Метод для получения массива меню.
     *
     * @return длину массива
     */
    public int getActionsLentgh() {
        return this.actions.size();
    }

   /**
     * Метод заполняет массив.
     */
    public void fillActions(StartUI startUI) {
        this.actions.add(new AddItem(0, "Add items", output));
        this.actions.add(new ShowItems(1, "Show All items", output));
        this.actions.add(new EditItem(2, "Edit item", output));
        this.actions.add(new DeleteItem(3, "Delete item", output));
        this.actions.add(new FindItemById(4, "Find item by Id", output));
        this.actions.add(new FindItemsByName(5, "Find Items By Name", output));
        this.actions.add(new ExitProgram(6, "Exit Program", output, startUI));
    }

    /**
     * Метод в зависимости от указанного ключа, выполняет соотвествующие действие.
     * @param key ключ операции
     */
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }
    /**
     * Метод выводит на экран меню.
     */
    public void show() {
        actions.forEach(action -> output.accept(action.info()));
    }
}
