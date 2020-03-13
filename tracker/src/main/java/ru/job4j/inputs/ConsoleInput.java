package ru.job4j.inputs;

import ru.job4j.exception.MenuOutException;

import java.util.*;

public class ConsoleInput implements Input {
    private Scanner scanner = new Scanner(System.in);

    public String ask(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }
    public int ask(String question, int[] range) {
        boolean flag = false;
        int key = Integer.valueOf(this.ask(question));
        for (int value : range) {
            if (value == key) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new MenuOutException("Введенное значение вызходит из значения диапазона");
        }
        return key;
    }
}
