package com.example.paragonapp;

import java.util.ArrayList;
import java.util.List;

public class Order {
    List cart = new ArrayList<String>();
    String name, totalBeforeTax, totalAfterTax, taxAmount;

    public Order() {
    }

    public Order(List cart, String name, String totalBeforeTax, String totalAfterTax, String taxAmount) {
        this.cart = cart;
        this.name = name;
        this.totalBeforeTax = totalBeforeTax;
        this.totalAfterTax = totalAfterTax;
        this.taxAmount = taxAmount;
    }

    public List getCart() {
        return cart;
    }

    public void setCart(List cart) {
        this.cart = cart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalBeforeTax() {
        return totalBeforeTax;
    }

    public void setTotalBeforeTax(String totalBeforeTax) {
        this.totalBeforeTax = totalBeforeTax;
    }

    public String getTotalAfterTax() {
        return totalAfterTax;
    }

    public void setTotalAfterTax(String totalAfterTax) {
        this.totalAfterTax = totalAfterTax;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }
}
