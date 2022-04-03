package ru.orioninno.frontend;

import ru.orioninno.services.OrderService;
import com.vaadin.flow.router.Route;

@Route("orders")
public class MyOrderView extends OrderView {
    public MyOrderView(OrderService orderService) {
        super(orderService);
    }
}
