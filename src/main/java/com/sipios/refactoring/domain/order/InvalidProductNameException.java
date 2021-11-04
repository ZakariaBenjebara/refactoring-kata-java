package com.sipios.refactoring.domain.order;

public class InvalidProductNameException extends RuntimeException {
    public InvalidProductNameException(String nameOfProduct) {
        super("Invalid name of product " + nameOfProduct);
    }
}
