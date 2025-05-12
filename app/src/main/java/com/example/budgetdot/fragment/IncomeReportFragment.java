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
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class IncomeReportFragment extends Fragment {

    private HorizontalBarChart barChart;
    private RecyclerView rvCategories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_report, container, false);

        barChart = view.findViewById(R.id.bar_chart);
        rvCategories = view.findViewById(R.id.rv_categories);

        setupBarChart();
        setupCategoryList();

        return view;
    }

    private void setupBarChart() {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 2000f)); // Salary
        entries.add(new BarEntry(1f, 500f));  // Freelance
        entries.add(new BarEntry(2f, 300f));  // Investments

        BarDataSet dataSet = new BarDataSet(entries, "Income by Category");
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
        categories.add(new CategoryReport("Salary", 2000, 2000, R.color.green_500));
        categories.add(new CategoryReport("Freelance", 500, 1000, R.color.blue_500));
        categories.add(new CategoryReport("Investments", 300, 500, R.color.orange_500));

        CategoryReportAdapter adapter = new CategoryReportAdapter(categories);
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategories.setAdapter(adapter);
    }
}