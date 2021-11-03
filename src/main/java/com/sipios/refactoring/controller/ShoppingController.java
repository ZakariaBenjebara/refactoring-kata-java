package com.sipios.refactoring.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.sipios.refactoring.customer.CustomerMembershipFactory;
import com.sipios.refactoring.controller.dto.OrderItem;
import com.sipios.refactoring.controller.dto.OrderRequest;
import com.sipios.refactoring.customer.MembershipMaximumPriceExceeded;
import com.sipios.refactoring.customer.MembershipPriceThresholdValidator;
import com.sipios.refactoring.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static com.sipios.refactoring.order.ProductFactory.createFrom;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {
    private static final String EMPTY_PRICE = "0";

    private Logger LOGGER = LoggerFactory.getLogger(ShoppingController.class);

    private final Supplier<LocalDate> nowSupplier;

    @Autowired
    public ShoppingController(Supplier<LocalDate> nowSupplier) {
        this.nowSupplier = nowSupplier;
    }

    @PostMapping
    public String getPrice(@RequestBody OrderRequest orderRequest) {
        if (null == orderRequest || orderRequest.getItems() == null) {
            return EMPTY_PRICE;
        }

        String customerType = orderRequest.getType();

        // Compute discountRate for customer
        var customerMembership = CustomerMembershipFactory.createFrom(customerType);
        var membershipPriceThresholdValidator = new MembershipPriceThresholdValidator(customerMembership);

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        var order = new Order(mapOrderItems(orderRequest.getItems()),
            customerMembership, nowSupplier.get());
        try {
            membershipPriceThresholdValidator.check(order.totalPrice());
        } catch (MembershipMaximumPriceExceeded e) {
            LOGGER.error("Invalid total price ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return String.valueOf(order.totalPrice());
    }

    private static List<com.sipios.refactoring.order.OrderItem> mapOrderItems(
        OrderItem[] items) {
        return Arrays.stream(items)
            .map(orderItemDto -> new com.sipios.refactoring.order.OrderItem(
                createFrom(orderItemDto.getType()), orderItemDto.getNb()))
            .collect(toList());
    }
}
