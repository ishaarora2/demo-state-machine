package com.tanzu.demo.controller;

import com.tanzu.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/create")
    public String createOrder(){
        return orderService.createOrder();
    }

    @GetMapping("process/{orderId}")
    public String processOrder(@PathVariable String orderId){
        return orderService.processOrder(orderId);
    }

    @GetMapping("deliver/{orderId}")
    public String deliverOrder(@PathVariable String orderId){
        return orderService.deliverOrder(orderId);
    }
}
