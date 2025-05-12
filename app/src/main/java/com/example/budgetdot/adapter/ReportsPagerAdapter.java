package com.example.budgetdot.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.budgetdot.fragment.OverviewReportFragment;
import com.example.budgetdot.fragment.ExpensesReportFragment;
import com.example.budgetdot.fragment.IncomeReportFragment;

public class ReportsPagerAdapter extends FragmentStateAdapter {

    public ReportsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OverviewReportFragment();
            case 1:
                return new ExpensesReportFragment();
            case 2:
                return new IncomeReportFragment();
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Overview, Expenses, Income
    }
}