package ru.orioninno.controllers;

import ru.orioninno.entities.Order;
import ru.orioninno.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public List<Order> findAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders;
    }

}
