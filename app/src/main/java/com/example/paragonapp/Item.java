package com.example.paragonapp;

public class Item {
    private String name, price, id;

    public Item(String name, String price, String id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public Item(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
