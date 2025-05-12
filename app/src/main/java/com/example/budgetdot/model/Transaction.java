package com.example.budgetdot.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.budgetdot.database.Converters;

import java.util.Date;

@Entity(tableName = "transactions")
@TypeConverters(Converters.class)
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String category;
    private double amount;
    private Date date;
    private String note;
    private String type; // "income" or "expense"
    private String account;

    public Transaction(String title, String category, double amount, Date date, String type, String account) {
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.account = account;
    }

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }
}