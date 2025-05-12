package com.example.budgetdot.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.budgetdot.activity.TransactionDetailActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.budgetdot.activity.BudgetDetailActivity;

import com.example.budgetdot.R;
import com.example.budgetdot.adapter.BudgetAdapter;
import com.example.budgetdot.adapter.TransactionAdapter;
import com.example.budgetdot.viewmodel.TransactionViewModel;
import com.example.budgetdot.viewmodel.BudgetViewModel;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvTransactions;
    private RecyclerView rvBudgets;
    private TransactionAdapter transactionAdapter;
    private BudgetAdapter budgetAdapter;
    private TextView tvBalance, tvIncome, tvExpense;

    private TransactionViewModel transactionViewModel;
    private BudgetViewModel budgetViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        rvTransactions = findViewById(R.id.rv_transactions);
        rvBudgets = findViewById(R.id.rv_budgets);
        tvBalance = findViewById(R.id.tv_balance);
        tvIncome = findViewById(R.id.tv_income);
        tvExpense = findViewById(R.id.tv_expense);

        // Setup ViewModels
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);

        // Setup transactions recycler view
        setupTransactionsRecyclerView();

        // Setup budgets recycler view
        setupBudgetsRecyclerView();

        // Observe data changes
        observeData();
    }

    private void setupTransactionsRecyclerView() {
        transactionAdapter = new TransactionAdapter();
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        rvTransactions.setAdapter(transactionAdapter);

        // Set click listener for transactions
        transactionAdapter.setOnItemClickListener(transaction -> {
            Intent intent = new Intent(MainActivity.this, TransactionDetailActivity.class);
            intent.putExtra("transaction_id", transaction.getId());
            startActivity(intent);
        });
    }

    private void setupBudgetsRecyclerView() {
        budgetAdapter = new BudgetAdapter();
        rvBudgets.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvBudgets.setAdapter(budgetAdapter);

        // Set click listener for budgets
        budgetAdapter.setOnItemClickListener(budget -> {
            Intent intent = new Intent(MainActivity.this, BudgetDetailActivity.class);
            intent.putExtra("budget_id", budget.getId());
            startActivity(intent);
        });
    }

    private void observeData() {
        // Observe transactions
        transactionViewModel.getAllTransactions().observe(this, transactions -> {
            transactionAdapter.setTransactions(transactions);
        });

        // Observe income and expenses
        transactionViewModel.getTotalIncome().observe(this, income -> {
            if (income != null) {
                tvIncome.setText(formatCurrency(income));
                updateBalance();
            }
        });

        transactionViewModel.getTotalExpenses().observe(this, expense -> {
            if (expense != null) {
                tvExpense.setText(formatCurrency(Math.abs(expense)));
                updateBalance();
            }
        });

        // Observe budgets
        budgetViewModel.getAllBudgets().observe(this, budgets -> {
            budgetAdapter.setBudgets(budgets);
        });
    }

    private void updateBalance() {
        Double income = transactionViewModel.getTotalIncome().getValue();
        Double expense = transactionViewModel.getTotalExpenses().getValue();

        if (income != null && expense != null) {
            double balance = income + expense; // Expense is negative
            tvBalance.setText(formatCurrency(balance));
        }
    }

    private String formatCurrency(double amount) {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(amount);
    }

    public void onAddTransactionClick(View view) {
        startActivity(new Intent(this, AddTransactionActivity.class));
    }

    public void onViewAllTransactionsClick(View view) {
        startActivity(new Intent(this, TransactionDetailActivity.class));
    }

    public void onViewAllBudgetsClick(View view) {
        startActivity(new Intent(this, BudgetDetailActivity.class));
    }
}