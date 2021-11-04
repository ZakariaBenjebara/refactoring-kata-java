package com.sipios.refactoring.domain.order;

import java.util.Objects;

public abstract class Product {
    private final ProductName name;

    protected Product(String name) {
        this.name = new ProductName(name);
    }

    public abstract double price();

    final ProductName name() {
        return name;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public final String toString() {
        return "Product{" +
            "name='" + name + '\'' +
            "price='" + price() + '\'' +
            '}';
    }
}
