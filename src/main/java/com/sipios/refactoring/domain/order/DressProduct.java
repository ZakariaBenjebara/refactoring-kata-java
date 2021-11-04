package com.sipios.refactoring.domain.order;

final class DressProduct extends Product {

    public DressProduct() {
        super("DRESS");
    }

    @Override
    public double price() {
        return 50;
    }
}
