package com.example.budgetdot.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.budgetdot.model.Transaction;
import com.example.budgetdot.repository.TransactionRepository;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionRepository repository;
    private LiveData<List<Transaction>> allTransactions;
    private LiveData<Double> totalIncome;
    private LiveData<Double> totalExpenses;

    public TransactionViewModel(Application application) {
        super(application);
        repository = new TransactionRepository(application);
        allTransactions = repository.getAllTransactions();
        totalIncome = repository.getTotalIncome();
        totalExpenses = repository.getTotalExpenses();
    }

    public void insert(Transaction transaction) {
        repository.insert(transaction);
    }

    public void update(Transaction transaction) {
        repository.update(transaction);
    }

    public void delete(Transaction transaction) {
        repository.delete(transaction);
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    public LiveData<Double> getTotalIncome() {
        return totalIncome;
    }

    public LiveData<Double> getTotalExpenses() {
        return totalExpenses;
    }
    public LiveData<Transaction> getTransactionById(long id) {
        return repository.getTransactionById(id);
    }

    public LiveData<List<Transaction>> getTransactionsByCategory(String category) {
        return repository.getTransactionsByCategory(category);
    }
}