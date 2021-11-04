package com.sipios.refactoring.controller;

import com.sipios.refactoring.domain.customer.InvalidMembershipException;
import com.sipios.refactoring.domain.customer.MembershipMaximumPriceExceeded;
import com.sipios.refactoring.domain.order.InvalidProductNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
        MembershipMaximumPriceExceeded.class,
        InvalidMembershipException.class,
        InvalidProductNameException.class
    })
    public ResponseEntity<Object> handleMembershipMaximumPriceExceeded(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
