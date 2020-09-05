package ru.job4j;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Описание заявки пользователя
 * @author  Denis Krylov
 * @version $Id$
 */
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String desc;
    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now().withNano(0);

    public Item() {

    }

    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public Item(Integer id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    /**
     * Метод задает значение поля this.id по входящему параметру
     * @param id новый id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Получить значение this.id
     */
    public String getId() {
        return this.id.toString();
    }
    /**
     *Получить значение поля this.name
     */
    public String getName() {
        return this.name;
    }
    /**
     * Метод задает значение поля this.name по входящему параметру
     * @param name новый name
     */
    public void setName(String name) {

        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    @Override
    public String toString() {
        return "Наменование заявки: " + this.getName() + " ID заявки: " + this.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(id, item.id)
                && Objects.equals(name, item.name)
                && Objects.equals(desc, item.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc);
    }
}
