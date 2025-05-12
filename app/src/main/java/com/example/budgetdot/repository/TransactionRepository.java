package com.example.budgetdot.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.budgetdot.database.AppDatabase;
import com.example.budgetdot.database.TransactionDao;
import com.example.budgetdot.model.Transaction;
import com.example.budgetdot.util.DateUtil;

import java.util.Date;
import java.util.List;

public class TransactionRepository {
    private TransactionDao transactionDao;
    private LiveData<List<Transaction>> allTransactions;
    private LiveData<Double> totalIncome;
    private LiveData<Double> totalExpenses;

    public TransactionRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        transactionDao = database.transactionDao();
        allTransactions = transactionDao.getRecentTransactions();

        // Get current month's data
        Date now = new Date();
        Date startOfMonth = DateUtil.getStartOfMonth(now);
        Date endOfMonth = DateUtil.getEndOfMonth(now);

        totalIncome = transactionDao.getTotalIncome(startOfMonth.getTime(), endOfMonth.getTime());
        totalExpenses = transactionDao.getTotalExpenses(startOfMonth.getTime(), endOfMonth.getTime());
    }

    public void insert(Transaction transaction) {
        new InsertTransactionAsyncTask(transactionDao).execute(transaction);
    }

    public void update(Transaction transaction) {
        new UpdateTransactionAsyncTask(transactionDao).execute(transaction);
    }

    public void delete(Transaction transaction) {
        new DeleteTransactionAsyncTask(transactionDao).execute(transaction);
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

    private static class InsertTransactionAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private TransactionDao transactionDao;

        private InsertTransactionAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDao.insert(transactions[0]);
            return null;
        }
    }

    private static class UpdateTransactionAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private TransactionDao transactionDao;

        private UpdateTransactionAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDao.update(transactions[0]);
            return null;
        }
    }

    private static class DeleteTransactionAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private TransactionDao transactionDao;

        private DeleteTransactionAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDao.delete(transactions[0].getId());
            return null;
        }
    }
    public LiveData<Transaction> getTransactionById(long id) {
        return transactionDao.getTransactionById(id);
    }

    public LiveData<List<Transaction>> getTransactionsByCategory(String category) {
        return transactionDao.getTransactionsByCategory(category);
    }
}