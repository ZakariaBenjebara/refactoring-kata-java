package com.sipios.refactoring.order;

import java.util.Objects;

final class ProductRebate {
    private final String productName;
    private final double discount;

    ProductRebate(String productName, double discount) {
        this.productName = productName;
        if (discount > 1 || discount <= 0) {
            throw new IllegalStateException("Invalid discount value");
        }
        this.discount = discount;
    }

    boolean isAllowedRebate(String productName) {
        return this.productName.equals(productName);
    }

    double apply(double price) {
        return price * discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRebate that = (ProductRebate) o;
        return Double.compare(that.discount, discount) == 0 && Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, discount);
    }
}