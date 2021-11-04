package com.sipios.refactoring.domain.order;

import java.util.Objects;

public final class OrderItem {
    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    double calculatePrice(ProductRebater productRebater) {
        double totalPrice = product.price() * quantity;
        return productRebater.applyRebates(product.name(), totalPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return quantity == orderItem.quantity && Objects.equals(product, orderItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
            "product=" + product +
            ", quantity=" + quantity +
            '}';
    }
}
