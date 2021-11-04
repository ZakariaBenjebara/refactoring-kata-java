package com.sipios.refactoring.controller;

import com.sipios.refactoring.controller.dto.OrderItem;
import com.sipios.refactoring.controller.dto.OrderRequest;
import com.sipios.refactoring.service.ShoppingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.sipios.refactoring.domain.order.ProductFactory.createFrom;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {
    private static final String EMPTY_PRICE = "0";

    private final ShoppingService shoppingService;

    @Autowired
    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @PostMapping
    public String getPrice(@RequestBody OrderRequest orderRequest) {
        if (null == orderRequest || orderRequest.getItems() == null) {
            return EMPTY_PRICE;
        }
        return String.valueOf(shoppingService.calculateTotalPrice(orderRequest.getType(),
            mapOrderItems(orderRequest.getItems())));
    }

    private static List<com.sipios.refactoring.domain.order.OrderItem> mapOrderItems(
        OrderItem[] items) {
        return Arrays.stream(items)
            .map(orderItemDto -> new com.sipios.refactoring.domain.order.OrderItem(
                createFrom(orderItemDto.getType()), orderItemDto.getNb()))
            .collect(toList());
    }
}
