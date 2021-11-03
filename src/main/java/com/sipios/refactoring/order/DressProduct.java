package com.sipios.refactoring.order;

public final class DressProduct extends Product {

    public DressProduct() {
        super("DRESS");
    }

    @Override
    public double price() {
        return 50;
    }
}
