package com.example.budgetdot.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.budgetdot.model.Budget;
import com.example.budgetdot.repository.BudgetRepository;

import java.util.List;

public class BudgetViewModel extends AndroidViewModel {
    private BudgetRepository repository;
    private LiveData<List<Budget>> allBudgets;

    public BudgetViewModel(Application application) {
        super(application);
        repository = new BudgetRepository(application);
        allBudgets = repository.getAllBudgets();
    }

    public void insert(Budget budget) {
        repository.insert(budget);
    }

    public void update(Budget budget) {
        repository.update(budget);
    }

    public void delete(Budget budget) {
        repository.delete(budget);
    }

    public LiveData<List<Budget>> getAllBudgets() {
        return allBudgets;
    }

    public LiveData<Budget> getBudgetById(long id) {
        return repository.getBudgetById(id);
    }
}