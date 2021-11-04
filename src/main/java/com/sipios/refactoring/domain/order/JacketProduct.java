package com.sipios.refactoring.domain.order;

final class JacketProduct extends Product {

    public JacketProduct() {
        super("JACKET");
    }

    @Override
    public double price() {
        return 100;
    }
}
