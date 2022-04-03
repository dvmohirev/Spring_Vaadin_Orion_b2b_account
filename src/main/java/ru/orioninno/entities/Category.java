package ru.orioninno.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category { //Сущность Категория - нужна для создания категорий товаров
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //Столбец ID в таблице в БД
    private Long id; // Создаем переменную ID

    @Column(name = "title")  //Столбец Название в таблице в БД
    private String title;

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "products_categories",
    joinColumns = @JoinColumn(name = "category_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;*/
}
