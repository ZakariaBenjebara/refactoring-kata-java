package com.sipios.refactoring.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.sipios.refactoring.domain.customer.CustomerMembershipFactory.createFrom;

class OrderTests {

    private static final LocalDate CURRENT_DATE
        = LocalDate.ofInstant(Instant.parse("2021-11-03T20:00:00.00Z"),
        ZoneId.of("Europe/Paris"));
    private static final List<ProductRebate> REBATES = List.of(
        new ProductRebate(new ProductName("JACKET"), 0.9),
        new ProductRebate(new ProductName("DRESS"), 0.8)
    );

    @Test
    void shouldCalculateOrderForStandardCustomer() {
        Order order = new Order(List.of(
                new OrderItem(new JacketProduct(), 3),
                new OrderItem(new DressProduct(), 5)
            ),
            createFrom("STANDARD_CUSTOMER"),
            CURRENT_DATE, REBATES);

        double price = order.totalPrice();
        Assertions.assertThat(price).isEqualTo(550.0);
    }

    @Test
    void shouldCalculateOrderForPremiumCustomer() {
        Order order = new Order(List.of(
            new OrderItem(new JacketProduct(), 3),
            new OrderItem(new DressProduct(), 5)
        ),
            createFrom("PREMIUM_CUSTOMER"),
            CURRENT_DATE, REBATES);

        double price = order.totalPrice();
        Assertions.assertThat(price).isEqualTo(495.0);
    }

    @Test
    void shouldCalculateOrderForPlatinumCustomer() {
        Order order = new Order(List.of(
            new OrderItem(new JacketProduct(), 3),
            new OrderItem(new DressProduct(), 5)
        ),
            createFrom("PLATINUM_CUSTOMER"),
            CURRENT_DATE, REBATES);

        double price = order.totalPrice();
        Assertions.assertThat(price).isEqualTo(275.0);
    }

    @Test
    void shouldAnyCustomerHasEligibilityForWinterDiscount() {
        LocalDate nowDate
            = LocalDate.ofInstant(Instant.parse("2021-01-14T20:00:00.00Z"),
            ZoneId.of("Europe/Paris"));
        Order order = new Order(List.of(
            new OrderItem(new JacketProduct(), 3),
            new OrderItem(new DressProduct(), 5)
        ),
            createFrom("STANDARD_CUSTOMER"),
            nowDate, REBATES);

        double price = order.totalPrice();
        Assertions.assertThat(price).isEqualTo(470.0);
    }

    @Test
    void shouldAnyCustomerHasEligibilityForSummerDiscount() {
        LocalDate nowDate
            = LocalDate.ofInstant(Instant.parse("2021-06-06T20:00:00.00Z"),
            ZoneId.of("Europe/Paris"));
        Order order = new Order(List.of(
            new OrderItem(new JacketProduct(), 3),
            new OrderItem(new DressProduct(), 5),
            new OrderItem(new TshirtProduct(), 10)
        ),
            createFrom("PLATINUM_CUSTOMER"),
            nowDate, REBATES);

        double price = order.totalPrice();
        Assertions.assertThat(price).isEqualTo(385.0);
    }
}
