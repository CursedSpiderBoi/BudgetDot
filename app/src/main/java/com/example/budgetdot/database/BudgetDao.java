package com.example.budgetdot.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.budgetdot.model.Budget;

import java.util.List;

@Dao
public interface BudgetDao {
    @Insert
    void insert(Budget budget);

    @Update
    void update(Budget budget);

    @Query("DELETE FROM budgets WHERE id = :id")
    void delete(long id);

    @Query("SELECT * FROM budgets ORDER BY category")
    LiveData<List<Budget>> getAllBudgets();

    @Query("SELECT * FROM budgets WHERE category = :category LIMIT 1")
    LiveData<Budget> getBudgetByCategory(String category);

    @Query("SELECT * FROM budgets WHERE id = :id LIMIT 1")
    LiveData<Budget> getBudgetById(long id);
}