package com.example.budgetdot.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetdot.R;
import com.example.budgetdot.adapter.TransactionAdapter;
import com.example.budgetdot.model.Budget;
import com.example.budgetdot.util.CurrencyUtil;
import com.example.budgetdot.viewmodel.BudgetViewModel;
import com.example.budgetdot.viewmodel.TransactionViewModel;

import java.util.Objects;

public class BudgetDetailActivity extends AppCompatActivity {

    private BudgetViewModel budgetViewModel;
    private TransactionViewModel transactionViewModel;
    private TransactionAdapter transactionAdapter;
    private Budget budget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_detail);

        // Setup toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Initialize ViewModels
        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Get budget ID from intent
        long budgetId = getIntent().getLongExtra("budget_id", -1);

        if (budgetId == -1) {
            finish();
            return;
        }

        // Setup transactions recycler view
        setupTransactionsRecyclerView();

        // Load budget details
        budgetViewModel.getBudgetById(budgetId).observe(this, this::populateViews);
    }

    private void setupTransactionsRecyclerView() {
        transactionAdapter = new TransactionAdapter();
        RecyclerView rvTransactions = findViewById(R.id.rv_transactions);
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        rvTransactions.setAdapter(transactionAdapter);
    }

    private void populateViews(Budget budget) {
        this.budget = budget;

        TextView tvCategory = findViewById(R.id.tv_category);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        TextView tvLimit = findViewById(R.id.tv_limit);
        TextView tvSpent = findViewById(R.id.tv_spent);
        TextView tvRemaining = findViewById(R.id.tv_remaining);
        TextView tvPeriod = findViewById(R.id.tv_period);

        tvCategory.setText(budget.getCategory());
        tvLimit.setText(CurrencyUtil.formatCurrency(budget.getLimit()));
        tvSpent.setText(CurrencyUtil.formatCurrency(budget.getSpent()));

        double remaining = budget.getLimit() - budget.getSpent();
        tvRemaining.setText(CurrencyUtil.formatCurrency(remaining));

        tvPeriod.setText(budget.getPeriod());

        int progress = (int) ((budget.getSpent() / budget.getLimit()) * 100);
        progressBar.setProgress(progress);

        // Change color based on progress
        int colorRes;
        if (progress > 90) {
            colorRes = R.color.red_500;
        } else if (progress > 70) {
            colorRes = R.color.orange_500;
        } else {
            colorRes = R.color.green_500;
        }
        progressBar.setProgressTintList(
                ContextCompat.getColorStateList(this, colorRes)
        );
        // Load transactions for this budget category
        transactionViewModel.getTransactionsByCategory(budget.getCategory())
                .observe(this, transactions -> {
                    transactionAdapter.setTransactions(transactions);
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_budget_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_edit) {
            editBudget();
            return true;
        } else if (id == R.id.action_delete) {
            deleteBudget();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void editBudget() {
        // Implement edit functionality
        Toast.makeText(this, "Edit budget", Toast.LENGTH_SHORT).show();
    }

    private void deleteBudget() {
        if (budget != null) {
            budgetViewModel.delete(budget);
            Toast.makeText(this, "Budget deleted", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}