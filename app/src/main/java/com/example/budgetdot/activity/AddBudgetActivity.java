package com.example.budgetdot.activity;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Spinner;
import android.widget.Toast;

//import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetdot.R;
import com.example.budgetdot.model.Budget;
import com.example.budgetdot.viewmodel.BudgetViewModel;

public class AddBudgetActivity extends AppCompatActivity {

    private BudgetViewModel budgetViewModel;
    private EditText etCategory, etLimit;
    private AutoCompleteTextView spPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        // Initialize ViewModel
        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);

        // Initialize views
        etCategory = findViewById(R.id.et_category);
        etLimit = findViewById(R.id.et_limit);
        spPeriod = findViewById(R.id.sp_period);
        Button btnSave = findViewById(R.id.btn_save);

        // Setup period spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.budget_periods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPeriod.setAdapter(adapter);

        // Set click listener for save button
        btnSave.setOnClickListener(v -> saveBudget());
    }

    private void saveBudget() {
        String category = etCategory.getText().toString().trim();
        String limitStr = etLimit.getText().toString().trim();
        String period = spPeriod.getText().toString().trim();

        // Validate inputs
        if (category.isEmpty()) {
            etCategory.setError("Category is required");
            return;
        }

        if (limitStr.isEmpty()) {
            etLimit.setError("Limit is required");
            return;
        }

        try {
            double limit = Double.parseDouble(limitStr);
            if (limit <= 0) {
                etLimit.setError("Must be greater than 0");
                return;
            }

            // Create new budget
            Budget budget = new Budget(category, limit, 0, period.toLowerCase());
            budgetViewModel.insert(budget);

            Toast.makeText(this, "Budget saved", Toast.LENGTH_SHORT).show();
            finish();

        } catch (NumberFormatException e) {
            etLimit.setError("Invalid amount");
        }
    }
}