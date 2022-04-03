package ru.orioninno.frontend;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.security.core.context.SecurityContextHolder;

public class TopMenuView extends AbstractView{

    public TopMenuView() {
    }

    protected HorizontalLayout initTopMenu() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(new Button("Главная", e -> UI.getCurrent().navigate("market")));
        horizontalLayout.add(new Button("Корзина", e -> UI.getCurrent().navigate("cart")));
        horizontalLayout.add(new Button("Мои заказы", e -> UI.getCurrent().navigate("orders")));
        horizontalLayout.add(new Button("Мой профиль", e -> UI.getCurrent().navigate("profile")));
        Button otherOrdersButton = new Button("Заказы пользователей", e -> UI.getCurrent().navigate("other-orders"));

        if (true/*isManagerOrAdmin()*/) {
            horizontalLayout.add(new Button("Добавить продукт", e -> UI.getCurrent().navigate("create-product")));
            horizontalLayout.add(otherOrdersButton);
        }

        horizontalLayout.add(new Button("Выход", e -> {
            SecurityContextHolder.clearContext();
            UI.getCurrent().navigate("login");
        }));

        return horizontalLayout;
        //add(horizontalLayout);
    }
}
