package com.sipios.refactoring.order;

import java.util.Objects;

public abstract class Product {
    private final String name;

    protected Product(String name) {
        this.name = name;
    }

    public abstract double price();

    public final String name() {
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
