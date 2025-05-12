package com.example.budgetdot.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String type; // "income" or "expense"
    private int color; // Color in hex

    public Category(String name, String type, int color) {
        this.name = name;
        this.type = type;
        this.color = color;
    }

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }
}