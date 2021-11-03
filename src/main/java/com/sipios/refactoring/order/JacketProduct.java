package com.sipios.refactoring.order;

public final class JacketProduct extends Product {

    public JacketProduct() {
        super("JACKET");
    }

    @Override
    public double price() {
        return 100;
    }
}
