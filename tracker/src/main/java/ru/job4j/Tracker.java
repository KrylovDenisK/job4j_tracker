package ru.job4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Tracker implements ITracker {
    private final List<Item> items = new ArrayList<>();
    private final Random random = new Random();
    /**
     * Метод реализаует добавление заявки в хранилище
     * @param item новая заявка
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        items.add(item);
        return item;
    }
    /**
     * Метод генерирует уникальный ключ для заявки.
     * @return Уникальный ключ.
     */
    private int generateId() {
        return random.nextInt();
    }
    /**
     * Поиск заявки по ID
     * @param id id заявки
     * @return найденная заявка
     */
    public Item findById(String id) {
        return items.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }
    /**
     * Метод ищет заявку по id и производит ее замену.
     * @param id   id заменяемой заявки
     * @param item новая заявка
     * @return true - опперация успешна, false - нет.
     */
    public boolean replace(String id, Item item) {
        boolean result = false;
        int index = IntStream.range(0, items.size()).filter(i -> items.get(i).getId().equals(id)).findFirst().orElse(-1);
        if (index >= 0) {
            item.setId(Integer.parseInt(id));
            items.set(index, item);
            result = true;
        }
        return result;
    }
    /**
     * Метод ищет заявку по id и производит ее удаление.
     * @param id id удаляемой заявки
     * @return true - опперация успешна, false - нет.
     */
    public boolean delete(String id) {
        return items.removeIf(item -> item.getId().equals(id));
    }
    /**
     * @return array возвращает копию массива this.items без null элементов
     */
    public List<Item> findAll() {
        return items;
    }
    /**
     * Метод отбирает заявки по key(this.name) и сохраняет в массив
     * @param key строка для поиска
     * @return array массив совпадений name
     */
    public List<Item> findByName(String key) {
        return items.stream().filter(item -> !Objects.isNull(item) && item.getName().equals(key)).collect(Collectors.toList());
    }
}


