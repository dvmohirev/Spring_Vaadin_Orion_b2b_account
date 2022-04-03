package ru.orioninno.frontend;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.orioninno.entities.Order;
import ru.orioninno.security.CustomPrincipal;
import ru.orioninno.security.SecurityUtils;
import ru.orioninno.services.OrderService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class OrderView extends AbstractView {
    protected final OrderService orderService;
    private final CustomPrincipal principal;

    protected Grid<Order> orderGrid;

    public OrderView(OrderService orderService) {
        this.orderService = orderService;
        this.principal = SecurityUtils.getPrincipal();
        initOrderView();
    }

    public void initOrderView() {
        // для теста размещаем на всех страницах
        TopMenuView topMenu = new TopMenuView();
        add(topMenu.initTopMenu());

        //Основная программа
        orderGrid = new Grid<>(Order.class);
        List<Order> order = orderService.getByUserName(principal.getUsername());
        orderGrid.setItems(order);
        orderGrid.setColumns("address", "items", "phoneNumber", "price", "status");

        Button backButton = new Button("Назад", event -> UI.getCurrent().navigate("market"));
        add(orderGrid, backButton);
    }
}
