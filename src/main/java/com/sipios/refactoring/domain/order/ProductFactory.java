package com.sipios.refactoring.domain.order;

public final class ProductFactory {
    private static final String JACKET_NAME = "JACKET";
    private static final String DRESS_NAME = "DRESS";
    private static final String TSHIRT_NAME = "TSHIRT";

    public static Product createFrom(String productName) {
        switch (productName) {
            case TSHIRT_NAME:
                return new TshirtProduct();
            case DRESS_NAME:
                return new DressProduct();
            case JACKET_NAME:
                return new JacketProduct();
            default:
                throw new InvalidProductNameException(productName);
        }
    }

    private ProductFactory() {
    }
}
