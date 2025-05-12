package com.example.budgetdot.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "budgets")
public class Budget {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String category;
    private double limit;
    private double spent;
    private String period; // "weekly", "monthly", "yearly"

    public Budget(String category, double limit, double spent, String period) {
        this.category = category;
        this.limit = limit;
        this.spent = spent;
        this.period = period;
    }

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getLimit() { return limit; }
    public void setLimit(double limit) { this.limit = limit; }
    public double getSpent() { return spent; }
    public void setSpent(double spent) { this.spent = spent; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}