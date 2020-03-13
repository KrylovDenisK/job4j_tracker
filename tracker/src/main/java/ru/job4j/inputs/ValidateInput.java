package ru.job4j.inputs;

import ru.job4j.exception.MenuOutException;

import java.util.function.Consumer;

public class ValidateInput implements Input {
    private final Input input;
    private final Consumer<String> output;

    public ValidateInput(final Input input, Consumer<String> output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }
    @Override
    public int ask(String question, int[] range) {
        boolean flag = true;
        int value = -1;
        do {
            try {
                value = this.input.ask(question, range);
                flag = false;
            } catch (NumberFormatException nfe) {
                output.accept("Необходимо ввести корректное значение");
            } catch (MenuOutException moe) {
                 output.accept("Необходимо выбрать значение из диапазона меню");
            }
        } while (flag);
        return value;
    }
}
