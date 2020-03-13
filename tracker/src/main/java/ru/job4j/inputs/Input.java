package ru.job4j.inputs;

public interface Input {
    String ask(String question);
    int ask(String question, int[] range);
}
