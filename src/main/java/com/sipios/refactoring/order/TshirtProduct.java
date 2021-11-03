package com.sipios.refactoring.order;

public final class TshirtProduct extends Product {

    public TshirtProduct() {
        super("TSHIRT");
    }

    @Override
    public double price() {
        return 30;
    }
}
