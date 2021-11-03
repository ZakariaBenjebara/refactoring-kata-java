package com.sipios.refactoring.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final double DRESS_SUMMER_DISCOUNT = 0.8;
    private static final double JACKET_SUMMER_DISCOUNT = 0.9;
    private static final int PREMIUM_CUSTOMER_MAX_PRICE_THRESHOLD = 800;
    private static final int PLATINUM_CUSTOMER_MAX_PRICE_THRESHOLD = 2000;
    private static final int STANDARD_CUSTOMER_MAX_PRICE_THRESHOLD = 200;
    private static final int JANUARY = 0;
    private static final int JUNE = 5;

    private Logger LOGGER = LoggerFactory.getLogger(ShoppingController.class);

    @PostMapping
    public String getPrice(@RequestBody OrderRequest orderRequest) {
        if (null == orderRequest || orderRequest.getItems() == null) {
            return EMPTY_PRICE;
        }

        double totalPrice = 0;
        double discountRate;

        Date date = new Date();
        Calendar currentDate = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        currentDate.setTime(date);

        // Compute discountRate for customer
        if (orderRequest.getType().equals(STANDARD_CUSTOMER_TYPE)) {
            discountRate = STANDARD_CUSTOMER_DISCOUNT_RATE;
        } else if (orderRequest.getType().equals(PREMIUM_CUSTOMER_TYPE)) {
            discountRate = PREMIUM_CUSTOMER_DISCOUNT_RATE;
        } else if (orderRequest.getType().equals(PLATINUM_CUSTOMER_TYPE)) {
            discountRate = PLATINUM_CUSTOMER_DISCOUNT_RATE;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        if (
            notWithinDiscountPeriod(currentDate, JUNE)
                && notWithinDiscountPeriod(currentDate, JANUARY)
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
                    totalPrice += DRESS_PRODUCT_PRICE * it.getNb() * DRESS_SUMMER_DISCOUNT * discountRate;
                } else if (it.getType().equals(JACKET_PRODUCT)) {
                    totalPrice += JACKET_PRICE_PRICE * it.getNb() * JACKET_SUMMER_DISCOUNT * discountRate;
                }
            }
        }

        try {
            if (orderRequest.getType().equals(STANDARD_CUSTOMER_TYPE)) {
                if (totalPrice > STANDARD_CUSTOMER_MAX_PRICE_THRESHOLD) {
                    throw new Exception("Price (" + totalPrice + ") is too high for standard customer");
                }
            } else if (orderRequest.getType().equals(PREMIUM_CUSTOMER_TYPE)) {
                if (totalPrice > PREMIUM_CUSTOMER_MAX_PRICE_THRESHOLD) {
                    throw new Exception("Price (" + totalPrice + ") is too high for premium customer");
                }
            } else if (orderRequest.getType().equals(PLATINUM_CUSTOMER_TYPE)) {
                if (totalPrice > PLATINUM_CUSTOMER_MAX_PRICE_THRESHOLD) {
                    throw new Exception("Price (" + totalPrice + ") is too high for platinum customer");
                }
            } else {
                if (totalPrice > STANDARD_CUSTOMER_MAX_PRICE_THRESHOLD) {
                    throw new Exception("Price (" + totalPrice + ") is too high for standard customer");
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(totalPrice);
    }

    private boolean notWithinDiscountPeriod(Calendar calendar, int month) {
        return !(
            calendar.get(Calendar.DAY_OF_MONTH) < 15 &&
                calendar.get(Calendar.DAY_OF_MONTH) > 5 &&
                calendar.get(Calendar.MONTH) == month
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
