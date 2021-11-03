package com.sipios.refactoring.order;

import java.util.Objects;

final class ProductName {
    private final String name;

    ProductName(String name) {
        this.name = name;
    }

    String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName product = (ProductName) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Product{" +
            "name='" + name + '\'' +
            '}';
    }
}
