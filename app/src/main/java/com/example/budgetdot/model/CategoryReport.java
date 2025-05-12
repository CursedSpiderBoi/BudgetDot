package com.example.budgetdot.model;

public class CategoryReport {
    private String category;
    private double amount;
    private double budget;
    private int colorRes;

    public CategoryReport(String category, double amount, double budget, int colorRes) {
        this.category = category;
        this.amount = amount;
        this.budget = budget;
        this.colorRes = colorRes;
    }

    // Getters
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public double getBudget() { return budget; }
    public int getColorRes() { return colorRes; }

    public double getPercentage() {
        return (amount / budget) * 100;
    }
}