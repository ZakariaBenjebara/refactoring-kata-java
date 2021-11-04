package com.sipios.refactoring.domain.order;

final class TshirtProduct extends Product {

    public TshirtProduct() {
        super("TSHIRT");
    }

    @Override
    public double price() {
        return 30;
    }
}
