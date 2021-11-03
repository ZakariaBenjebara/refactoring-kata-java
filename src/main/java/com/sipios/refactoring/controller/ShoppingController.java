package com.sipios.refactoring.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private static final int TSHIRT_PRODUCT_PRICE = 30;
    private static final int DRESS_PRODUCT_PRICE = 50;
    private static final int JACKET_PRICE_PRICE = 100;
    private static final String EMPTY_PRICE = "0";
    private static final String STANDARD_CUSTOMER_TYPE = "STANDARD_CUSTOMER";
    private static final String PREMIUM_CUSTOMER_TYPE = "PREMIUM_CUSTOMER";
    private static final String PLATINUM_CUSTOMER_TYPE = "PLATINUM_CUSTOMER";
    private static final int STANDARD_CUSTOMER_DISCOUNT_RATE = 1;
    private static final double PREMIUM_CUSTOMER_DISCOUNT_RATE = 0.9;
    private static final double PLATINUM_CUSTOMER_DISCOUNT_RATE = 0.5;
    private static final String TSHIRT_PRODUCT = "TSHIRT";
    private static final String DRESS_PRODUCT = "DRESS";
    private static final String JACKET_PRODUCT = "JACKET";
    private static final double DRESS_PERIOD_DISCOUNT = 0.8;
    private static final double JACKET_PERIOD_DISCOUNT = 0.9;
    private static final int PREMIUM_CUSTOMER_MAX_PRICE_THRESHOLD = 800;
    private static final int PLATINUM_CUSTOMER_MAX_PRICE_THRESHOLD = 2000;
    private static final int STANDARD_CUSTOMER_MAX_PRICE_THRESHOLD = 200;
    private static final int JANUARY = 0;
    private static final int JUNE = 5;

    private Logger LOGGER = LoggerFactory.getLogger(ShoppingController.class);

    private final Supplier<LocalDate> nowSupplier;

    @Autowired
    public ShoppingController(Supplier<LocalDate> nowSupplier) {
        this.nowSupplier = nowSupplier;
    }

    @PostMapping
    public String getPrice(@RequestBody OrderRequest orderRequest) {
        if (null == orderRequest || orderRequest.getItems() == null) {
            return EMPTY_PRICE;
        }

        double totalPrice = 0;
        String customerType = orderRequest.getType();

        // Compute discountRate for customer
        double discountRate = computeDiscountForCustomer(customerType);

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        if (
            notWithinDiscountPeriod(nowSupplier.get(), Month.JUNE)
                && notWithinDiscountPeriod(nowSupplier.get(), Month.JANUARY)
        ) {
            for (int i = 0; i < orderRequest.getItems().length; i++) {
                Item it = orderRequest.getItems()[i];

                if (it.getType().equals(TSHIRT_PRODUCT)) {
                    totalPrice += TSHIRT_PRODUCT_PRICE * it.getNb() * discountRate;
                } else if (it.getType().equals(DRESS_PRODUCT)) {
                    totalPrice += DRESS_PRODUCT_PRICE * it.getNb() * discountRate;
                } else if (it.getType().equals(JACKET_PRODUCT)) {
                    totalPrice += JACKET_PRICE_PRICE * it.getNb() * discountRate;
                }
            }
        } else {

            for (int i = 0; i < orderRequest.getItems().length; i++) {
                Item it = orderRequest.getItems()[i];

                if (it.getType().equals(TSHIRT_PRODUCT)) {
                    totalPrice += TSHIRT_PRODUCT_PRICE * it.getNb() * discountRate;
                } else if (it.getType().equals(DRESS_PRODUCT)) {
                    totalPrice += DRESS_PRODUCT_PRICE * it.getNb() * DRESS_PERIOD_DISCOUNT * discountRate;
                } else if (it.getType().equals(JACKET_PRODUCT)) {
                    totalPrice += JACKET_PRICE_PRICE * it.getNb() * JACKET_PERIOD_DISCOUNT * discountRate;
                }
            }
        }
        try {
            checkTotalPriceThresholdForCustomer(customerType, totalPrice);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(totalPrice);
    }

    private void checkTotalPriceThresholdForCustomer(String customerType,
                                                     double totalPrice) throws Exception {
        if (PREMIUM_CUSTOMER_TYPE.equals(customerType)) {
            if (totalPrice > PREMIUM_CUSTOMER_MAX_PRICE_THRESHOLD) {
                throw new Exception("Price (" + totalPrice + ") is too high for premium customer");
            }
        } else if (PLATINUM_CUSTOMER_TYPE.equals(customerType)) {
            if (totalPrice > PLATINUM_CUSTOMER_MAX_PRICE_THRESHOLD) {
                throw new Exception("Price (" + totalPrice + ") is too high for platinum customer");
            }
        } else {
            if (totalPrice > STANDARD_CUSTOMER_MAX_PRICE_THRESHOLD) {
                throw new Exception("Price (" + totalPrice + ") is too high for standard customer");
            }
        }
    }

    private double computeDiscountForCustomer(String customerType) {
        switch (customerType) {
            case STANDARD_CUSTOMER_TYPE:
                return STANDARD_CUSTOMER_DISCOUNT_RATE;
            case PREMIUM_CUSTOMER_TYPE:
                return PREMIUM_CUSTOMER_DISCOUNT_RATE;
            case PLATINUM_CUSTOMER_TYPE:
                return PLATINUM_CUSTOMER_DISCOUNT_RATE;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private boolean notWithinDiscountPeriod(LocalDate currentDate, Month month) {
        return !(
            currentDate.getDayOfMonth() < 15 &&
                currentDate.getDayOfMonth() > 5 &&
                currentDate.getMonth() == month
        );
    }
}

class OrderRequest {

    private Item[] items;
    private String type;

    public OrderRequest(Item[] is, String t) {
        this.items = is;
        this.type = t;
    }

    public OrderRequest() {}

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

class Item {

    private String type;
    private int nb;

    public Item() {}

    public Item(String type, int quantity) {
        this.type = type;
        this.nb = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }
}
