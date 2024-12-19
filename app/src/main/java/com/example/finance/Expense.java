package com.example.finance;

public class Expense {
    private String amount;
    private String category;
    private String date;

    public Expense(String amount, String category, String date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }
}