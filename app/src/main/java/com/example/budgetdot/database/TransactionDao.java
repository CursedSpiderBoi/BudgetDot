package com.example.budgetdot.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.budgetdot.model.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void insert(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Query("DELETE FROM transactions WHERE id = :id")
    void delete(long id);

    @Query("SELECT * FROM transactions ORDER BY date DESC LIMIT 10")
    LiveData<List<Transaction>> getRecentTransactions();

    @Query("SELECT * FROM transactions WHERE date BETWEEN :start AND :end ORDER BY date DESC")
    LiveData<List<Transaction>> getTransactionsByDate(long start, long end);

    @Query("SELECT SUM(amount) FROM transactions WHERE amount > 0 AND date BETWEEN :start AND :end")
    LiveData<Double> getTotalIncome(long start, long end);

    @Query("SELECT SUM(amount) FROM transactions WHERE amount < 0 AND date BETWEEN :start AND :end")
    LiveData<Double> getTotalExpenses(long start, long end);

    @Query("SELECT * FROM transactions WHERE category = :category ORDER BY date DESC")
    LiveData<List<Transaction>> getTransactionsByCategory(String category);

    @Query("SELECT * FROM transactions WHERE id = :id LIMIT 1")
    LiveData<Transaction> getTransactionById(long id);
}