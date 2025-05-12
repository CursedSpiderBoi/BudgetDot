package com.example.budgetdot.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.budgetdot.database.AppDatabase;
import com.example.budgetdot.database.BudgetDao;
import com.example.budgetdot.model.Budget;

import java.util.List;

public class BudgetRepository {
    private BudgetDao budgetDao;
    private LiveData<List<Budget>> allBudgets;

    public BudgetRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        budgetDao = database.budgetDao();
        allBudgets = budgetDao.getAllBudgets();
    }

    public void insert(Budget budget) {
        new InsertBudgetAsyncTask(budgetDao).execute(budget);
    }

    public void update(Budget budget) {
        new UpdateBudgetAsyncTask(budgetDao).execute(budget);
    }

    public void delete(Budget budget) {
        new DeleteBudgetAsyncTask(budgetDao).execute(budget);
    }

    public LiveData<List<Budget>> getAllBudgets() {
        return allBudgets;
    }

    public LiveData<Budget> getBudgetById(long id) {
        return budgetDao.getBudgetById(id);
    }

    private static class InsertBudgetAsyncTask extends AsyncTask<Budget, Void, Void> {
        private BudgetDao budgetDao;

        private InsertBudgetAsyncTask(BudgetDao budgetDao) {
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {
            budgetDao.insert(budgets[0]);
            return null;
        }
    }

    private static class UpdateBudgetAsyncTask extends AsyncTask<Budget, Void, Void> {
        private BudgetDao budgetDao;

        private UpdateBudgetAsyncTask(BudgetDao budgetDao) {
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {
            budgetDao.update(budgets[0]);
            return null;
        }
    }

    private static class DeleteBudgetAsyncTask extends AsyncTask<Budget, Void, Void> {
        private BudgetDao budgetDao;

        private DeleteBudgetAsyncTask(BudgetDao budgetDao) {
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {
            budgetDao.delete(budgets[0].getId());
            return null;
        }
    }
}