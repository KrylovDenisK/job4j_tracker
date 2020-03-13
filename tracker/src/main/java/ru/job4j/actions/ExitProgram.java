package ru.job4j.actions;

import ru.job4j.ITracker;
import ru.job4j.StartUI;
import ru.job4j.inputs.Input;

import java.util.function.Consumer;

public class ExitProgram extends BaseAction {
    private StartUI startUI;
    public ExitProgram(int key, String name, Consumer<String> output, StartUI startUI) {
        super(key, name, output);
        this.startUI = startUI;
    }
    @Override
    public void execute(Input input, ITracker tracker) {
       getOutput().accept("Завершение работы");
       startUI.setWork(false);
    }
}
