package com.example.budgetdot.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetdot.R;
import com.example.budgetdot.adapter.CategoryReportAdapter;
import com.example.budgetdot.model.CategoryReport;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ExpensesReportFragment extends Fragment {

    private BarChart barChart;
    private RecyclerView rvCategories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses_report, container, false);

        barChart = view.findViewById(R.id.bar_chart);
        rvCategories = view.findViewById(R.id.rv_categories);

        setupBarChart();
        setupCategoryList();

        return view;
    }

    private void setupBarChart() {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 500f)); // Food
        entries.add(new BarEntry(1f, 300f)); // Housing
        entries.add(new BarEntry(2f, 200f)); // Transportation
        entries.add(new BarEntry(3f, 150f)); // Entertainment
        entries.add(new BarEntry(4f, 100f)); // Utilities

        BarDataSet dataSet = new BarDataSet(entries, "Expenses by Category");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setEnabled(false);
        barChart.animateY(1000);
        barChart.invalidate();
    }

    private void setupCategoryList() {
        List<CategoryReport> categories = new ArrayList<>();
        categories.add(new CategoryReport("Food", 500, 800, R.color.green_500));
        categories.add(new CategoryReport("Housing", 300, 1200, R.color.blue_500));
        categories.add(new CategoryReport("Transportation", 200, 300, R.color.orange_500));
        categories.add(new CategoryReport("Entertainment", 150, 200, R.color.purple_500));
        categories.add(new CategoryReport("Utilities", 100, 150, R.color.red_500));

        CategoryReportAdapter adapter = new CategoryReportAdapter(categories);
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategories.setAdapter(adapter);
    }
}