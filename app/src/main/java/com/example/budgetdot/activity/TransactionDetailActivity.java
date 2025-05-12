package com.example.budgetdot.activity;

import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetdot.R;
import com.example.budgetdot.model.Transaction;
import com.example.budgetdot.util.CurrencyUtil;
import com.example.budgetdot.util.DateUtil;
import com.example.budgetdot.viewmodel.TransactionViewModel;

import java.util.Objects;

public class TransactionDetailActivity extends AppCompatActivity {

    private TransactionViewModel transactionViewModel;
    private Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        Toolbar toolbar = findViewById(R.id.toolbar); // <-- Get the toolbar
        setSupportActionBar(toolbar); // <-- CRUCIAL: Set the toolbar as the action bar


        // Setup toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Initialize ViewModel
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Get transaction ID from intent
        long transactionId = getIntent().getLongExtra("transaction_id", -1);

        if (transactionId == -1) {
            finish();
            return;
        }

        // Load transaction details
        transactionViewModel.getTransactionById(transactionId).observe(this, this::populateViews);
    }

    private void populateViews(Transaction transaction) {
        this.transaction = transaction;

        TextView tvAmount = findViewById(R.id.tv_amount);
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvCategory = findViewById(R.id.tv_category);
        TextView tvDate = findViewById(R.id.tv_date);
        TextView tvAccount = findViewById(R.id.tv_account);
        TextView tvType = findViewById(R.id.tv_type);
        TextView tvNote = findViewById(R.id.tv_note);

        tvAmount.setText(CurrencyUtil.formatCurrency(transaction.getAmount()));
        tvTitle.setText(transaction.getTitle());
        tvCategory.setText(transaction.getCategory());
        tvDate.setText(DateUtil.formatDate(transaction.getDate()));
        tvAccount.setText(transaction.getAccount());
        tvType.setText(transaction.getType().equals("income") ? "Income" : "Expense");

        if (transaction.getNote() != null && !transaction.getNote().isEmpty()) {
            tvNote.setText(transaction.getNote());
        } else {
            tvNote.setText("-");
        }

        // Set color based on transaction type
        int colorRes = transaction.getType().equals("income")
                ? R.color.green_500
                : R.color.red_500;
        tvAmount.setTextColor(getResources().getColor(colorRes));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_edit) {
            editTransaction();
            return true;
        } else if (id == R.id.action_delete) {
            deleteTransaction();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transaction_detail, menu);
        return true;
    }

    private void editTransaction() {
        // Implement edit functionality
        Toast.makeText(this, "Edit transaction", Toast.LENGTH_SHORT).show();
    }

    private void deleteTransaction() {
        if (transaction != null) {
            transactionViewModel.delete(transaction);
            Toast.makeText(this, "Transaction deleted", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}