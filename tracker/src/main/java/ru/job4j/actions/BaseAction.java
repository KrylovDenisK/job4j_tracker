package ru.job4j.actions;


import java.util.function.Consumer;

public abstract class BaseAction implements UserAction {
    private final int key;
    private final String name;
    private final Consumer<String> output;

    public BaseAction(final int key, final String name, Consumer<String> output) {
        this.key = key;
        this.name = name;
        this.output = output;
    }

   @Override
    public String info() {
       return String.format("%s. %s", this.key, this.name);
   }
   @Override
    public int key() {
        return this.key;
   }

   public Consumer<String> getOutput() {
        return output;
   }

}
