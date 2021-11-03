package com.sipios.refactoring.customer;

public final class InvalidMembershipException extends RuntimeException {
    public InvalidMembershipException(String typeOfMembership) {
        super("Invalid type of membership: " + typeOfMembership);
    }
}
