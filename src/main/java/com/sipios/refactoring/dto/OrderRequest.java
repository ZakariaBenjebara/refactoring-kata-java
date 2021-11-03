package com.sipios.refactoring.dto;

public class OrderRequest {

    private OrderItem[] items;
    private String type;

    public OrderRequest(OrderItem[] is, String t) {
        this.items = is;
        this.type = t;
    }

    public OrderRequest() {
    }

    public OrderItem[] getItems() {
        return items;
    }

    public void setItems(OrderItem[] items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
