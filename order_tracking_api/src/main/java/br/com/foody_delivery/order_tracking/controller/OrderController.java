package br.com.foody_delivery.order_tracking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping
    public String getOrder() {
        return "Order details";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder() {
        // Logic to create an order
        return ResponseEntity.ok("Order created successfully");
    }

}
