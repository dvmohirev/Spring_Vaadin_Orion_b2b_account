package ru.orioninno.frontend;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import ru.orioninno.entities.Product;
import ru.orioninno.repositories.ProductRepository;
import ru.orioninno.services.CartService;
import ru.orioninno.specifications.ProductFilter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static com.vaadin.flow.component.Tag.H3;

@Route("market")
public class MarketView extends AbstractView {

    private final CartService cartService;
    private final ProductRepository productRepository;
    private final Authentication authentication;

    private TextField titleTextField;
    private TextField minPriceTextField;
    private TextField maxPriceTextField;
    private Grid<Product> productGrid;
    private int counter = 0;

    public MarketView(CartService cartService,
                      ProductRepository productRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        initMarketPage();
    }



    private void initMarketPage() {
        TopMenuView topMenu = new TopMenuView();
        add(topMenu.initTopMenu());

        productGrid = new Grid<>(Product.class);
        productGrid.setWidth("100%");
        productGrid.setColumns("id", "title", "description", "price");
        productGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        List<Product> productList = productRepository.findAll();
        productGrid.setItems(productList);

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        setHorizontalComponentAlignment(Alignment.CENTER, productGrid);
        //add(horizontalLayout);
        add(new H2("????????????"));
        add(initFilterComponent());
        Button newButton = new Button("???????????????? ?????????????????? ????????????", e -> {
            Set<Product> productSet = productGrid.getSelectedItems();
            productSet.stream().forEach(cartService::add);
            Notification.show("?????????? ?????????????? ???????????????? ?? ??????????????");
        });

        Paragraph info = new Paragraph(infoText());
        newButton.addClickListener(clickEvent -> {
            counter = productGrid.getSelectedItems().size();
            info.setText(infoText());
        });
        add(new H2("???????????? ??????????????"), newButton, info);
        add(productGrid);
    }
    private String infoText() {
        if (counter == 1 || (counter != 11 & counter % 10 == 1)){
            return String.format("?????????????????? %d ??????????", counter);
        } else if (counter == 0){
            return String.format("?????????????????? %d ??????????????", counter);
        } else if (!(counter >=12 & counter <=14) || (counter % 10 >= 2 & counter % 10 <= 4)){
            return String.format("?????????????????? %d ????????????", counter);
        } else return String.format("?????????????????? %d ??????????????", counter);
    }
    /*private boolean isManagerOrAdmin() {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER")
                || a.getAuthority().equals("ROLE_ADMIN"));
    }*/

    private Component initFilterComponent() {

        HorizontalLayout priceLayout = new HorizontalLayout();
        titleTextField = initTextFieldWithPlaceholder("????????????????");
        minPriceTextField = initTextFieldWithPlaceholder("?????????????????????? ????????");
        maxPriceTextField = initTextFieldWithPlaceholder("???????????????????????? ????????");
        priceLayout.add(titleTextField, minPriceTextField, maxPriceTextField);
        priceLayout.setAlignItems(Alignment.CENTER);

        VerticalLayout categoriesLayout = new VerticalLayout();
        Label categoriesLabel = new Label("??????????????????");

        HorizontalLayout categoryHorLayout = new HorizontalLayout();
        Checkbox foodCheckbox = new Checkbox("??????");
        Checkbox devicesCheckbox = new Checkbox("??????????????????????");
        Checkbox transportCheckbox = new Checkbox("??????????????????");
        Checkbox mebelCheckbox = new Checkbox("????????????");
        Checkbox shoesCheckbox = new Checkbox("??????????");
        Checkbox clothesCheckbox = new Checkbox("????????????");
        Checkbox sportsCheckbox = new Checkbox("???????????????????? ????????????");


        categoryHorLayout.add(foodCheckbox, devicesCheckbox, transportCheckbox,
                mebelCheckbox, shoesCheckbox, clothesCheckbox, sportsCheckbox);
        categoriesLayout.add(categoriesLabel, categoryHorLayout);
        categoriesLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button makeFiltersButton = new Button("??????????????????", event -> {
            Map<String, String> filterMap = new HashMap<>();
            List<String> categories = new ArrayList<>();
            if (StringUtils.isNotEmpty(titleTextField.getValue())) {
                filterMap.put("title", titleTextField.getValue());
            }

            if (StringUtils.isNotEmpty(minPriceTextField.getValue())) {
                filterMap.put("min_price", minPriceTextField.getValue());
            }

            if (StringUtils.isNotEmpty(maxPriceTextField.getValue())) {
                filterMap.put("max_price", maxPriceTextField.getValue());
            }

            if (foodCheckbox.getValue()) {
                categories.add("Food");
            }

            if (devicesCheckbox.getValue()) {
                categories.add("Elektronika");
            }
            if (transportCheckbox.getValue()) {
                categories.add("Transport");
            }

            if (mebelCheckbox.getValue()) {
                categories.add("Mebel");
            }
            if (shoesCheckbox.getValue()) {
                categories.add("Shoes");
            }

            if (clothesCheckbox.getValue()) {
                categories.add("Clothes");
            }
            if (sportsCheckbox.getValue()) {
                categories.add("Sports");
            }

            productGrid.setItems(
                    productRepository.findAll(
                            new ProductFilter(filterMap, categories).getSpec(), PageRequest.of(0, 10)).getContent()
            );
        });

        Button cancelFiltersButton = new Button("?????????? ??????????????", event -> productGrid.setItems(productRepository.findAll()));
        buttonLayout.add(makeFiltersButton, cancelFiltersButton);

        VerticalLayout filterComponentsLayout = new VerticalLayout(priceLayout, categoriesLayout, buttonLayout);
        filterComponentsLayout.setAlignItems(Alignment.CENTER);
        return filterComponentsLayout;
    }

}
