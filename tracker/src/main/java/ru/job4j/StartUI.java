package ru.job4j;

import ru.job4j.inputs.ConsoleInput;
import ru.job4j.inputs.Input;
import ru.job4j.inputs.ValidateInput;
import ru.job4j.sql.DataSource;
import ru.job4j.sql.TrackerSQL;

import java.util.function.Consumer;

public class StartUI {
    private boolean work = true;
    private final Input input;
    private final Consumer<String> output;
    /**
     * Хранилище заявок.
     */
    private final ITracker tracker;
    /**
     * Конструтор инициализирующий поля.
     * @param input ввод данных.
     * @param tracker хранилище заявок.
     */
    public StartUI(Input input, ITracker tracker, Consumer<String> output) {
        this.input = input;
        this.tracker = tracker;
        this.output = output;
    }

    public void setWork(boolean work) {
        this.work = work;
    }
    /**
     * Основой цикл программы.
     */
    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker, this.output);
        menu.fillActions(this);
        int[] range = new int[menu.getActionsLentgh()];
        for (int i = 0; i < menu.getActionsLentgh(); i++) {
            range[i] = i;
        }
        do {
            menu.show();
            menu.select(input.ask("select:", range));
        } while (work);
    }

        public static void main(String[] args) {
            try (TrackerSQL trackerSQL = new TrackerSQL(new DataSource().getConnection())) {
                new StartUI(new ValidateInput(new ConsoleInput(), System.out::println), trackerSQL, System.out::println).init();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

}
