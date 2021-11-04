package com.sipios.refactoring.domain.customer;

public final class InvalidMembershipException extends RuntimeException {
    public InvalidMembershipException(String typeOfMembership) {
        super("Invalid type of customer membership: " + typeOfMembership);
    }
}
