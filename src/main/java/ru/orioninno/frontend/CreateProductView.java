package ru.orioninno.frontend;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.orioninno.entities.Category;
import ru.orioninno.entities.Product;
import ru.orioninno.repositories.CategoryRepository;
import ru.orioninno.repositories.ProductRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Route("create-product")
public class CreateProductView extends AbstractView {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CreateProductView(CategoryRepository categoryRepository,
                             ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        initProductView();
    }

    private void initProductView() {
        // для теста размещаем на всех страницах
        TopMenuView topMenu = new TopMenuView();
        add(topMenu.initTopMenu());

        //Основная программа
        add(new H2("Добавление нового товара"));
        TextField titleTextField = initTextFieldWithPlaceholder("Заголовок");
        TextField priceTextField = initTextFieldWithPlaceholder("Цена");
        MultiSelectListBox<String> catMultiSelListBox = new MultiSelectListBox<>();
        List<Category> listOfCategory = categoryRepository.findAll();
        Set<String> setOfNameCategory = new HashSet<>();
        for (Category cate : listOfCategory){
            setOfNameCategory.add(cate.getTitle());
        }
        catMultiSelListBox.setItems(setOfNameCategory);
        MultiSelectListBox<Category> categoryMultiSelectListBox = new MultiSelectListBox<>();
        categoryMultiSelectListBox.setItems(new HashSet<>(categoryRepository.findAll()));

        Button saveButton = new Button("Сохранить", event -> {
            Product product = new Product();
            product.setTitle(titleTextField.getValue());
            product.setPrice(new BigDecimal(priceTextField.getValue()));
            product.setCategories(new ArrayList<>(categoryMultiSelectListBox.getValue()));

            productRepository.save(product);
            UI.getCurrent().navigate("market");
        });

        Button backButton = new Button("Назад", event -> {
            UI.getCurrent().navigate("market");
        });

        add(titleTextField, priceTextField, /*categoryMultiSelectListBox,*/ catMultiSelListBox, saveButton, backButton);
        setAlignItems(Alignment.CENTER);
    }
}
