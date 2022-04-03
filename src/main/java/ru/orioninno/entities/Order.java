package ru.orioninno.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order { //Сущность Заказ - нужна для создания заказов в ИМ
    @Id // Аннотация ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Генерируем какое-то значение для ID
    @Column(name = "id") // Столбец ID в таблице в БД
    private Long id; // Создаем переменную ID обертки Long

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // Создаем переменную user класса User

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "order_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name =  "item_id"))
    private List<OrderItem> items;

    private String phoneNumber; // Создаем переменную phoneNumber класса String

    private String address; // Создаем переменную address класса String

    private BigDecimal price; // Создаем переменную price класса BigDecimal

    private Status status; // Создаем переменную status enum класса Status

    public enum Status {
        MANAGING, DELIVERING, DELIVERED
    }

}
