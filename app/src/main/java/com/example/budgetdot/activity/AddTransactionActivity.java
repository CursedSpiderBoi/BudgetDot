package com.example.budgetdot.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;

import com.example.budgetdot.R;
import com.example.budgetdot.model.Transaction;
import com.example.budgetdot.util.DateUtil;
import com.example.budgetdot.viewmodel.TransactionViewModel;

import java.util.Calendar;
import java.util.Date;

public class AddTransactionActivity extends AppCompatActivity {

    private EditText etTitle, etAmount, etNote;
    private Spinner spType, spCategory, spAccount;
    private Button btnDate, btnSave;
    private Date selectedDate;

    private TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        // Initialize ViewModel
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Initialize views
        etTitle = findViewById(R.id.et_title);
        etAmount = findViewById(R.id.et_amount);
        etNote = findViewById(R.id.et_note);
        spType = findViewById(R.id.sp_type);
        spCategory = findViewById(R.id.sp_category);
        spAccount = findViewById(R.id.sp_account);
        btnDate = findViewById(R.id.btn_date);
        btnSave = findViewById(R.id.btn_save);

        // Set current date as default
        selectedDate = new Date();
        updateDateButton();

        // Setup spinners
        setupSpinners();

        // Set click listeners
        btnDate.setOnClickListener(v -> showDatePicker());
        btnSave.setOnClickListener(v -> saveTransaction());
    }

    private void setupSpinners() {
        // Type spinner
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.transaction_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(typeAdapter);

        // Category spinner (will be populated based on type selection)
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.expense_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(categoryAdapter);

        // Account spinner
        ArrayAdapter<CharSequence> accountAdapter = ArrayAdapter.createFromResource(this,
                R.array.accounts, android.R.layout.simple_spinner_item);
        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAccount.setAdapter(accountAdapter);

        // Update categories when type changes
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int categoryArray = position == 0 ? R.array.expense_categories : R.array.income_categories;
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                        AddTransactionActivity.this, categoryArray, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCategory.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    selectedDate = calendar.getTime();
                    updateDateButton();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDateButton() {
        btnDate.setText(DateUtil.formatDate(selectedDate));
    }

    private void saveTransaction() {
        String title = etTitle.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();
        String note = etNote.getText().toString().trim();
        String type = spType.getSelectedItem().toString();
        String category = spCategory.getSelectedItem().toString();
        String account = spAccount.getSelectedItem().toString();

        if (title.isEmpty()) {
            etTitle.setError("Title is required");
            return;
        }

        if (amountStr.isEmpty()) {
            etAmount.setError("Amount is required");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (type.equals("Expense")) {
                amount = -Math.abs(amount); // Ensure negative for expenses
            }

            Transaction transaction = new Transaction(
                    title,
                    category,
                    amount,
                    selectedDate,
                    type.toLowerCase(),
                    account
            );
            transaction.setNote(note);

            transactionViewModel.insert(transaction);
            Toast.makeText(this, "Transaction saved", Toast.LENGTH_SHORT).show();
            finish();

        } catch (NumberFormatException e) {
            etAmount.setError("Invalid amount");
        }
    }
}