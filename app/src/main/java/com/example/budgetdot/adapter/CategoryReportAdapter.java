package com.example.budgetdot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetdot.R;
import com.example.budgetdot.model.CategoryReport;
import com.example.budgetdot.util.CurrencyUtil;

import java.util.List;

public class CategoryReportAdapter extends RecyclerView.Adapter<CategoryReportAdapter.ViewHolder> {

    private final List<CategoryReport> categories;

    public CategoryReportAdapter(List<CategoryReport> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryReport report = categories.get(position);

        holder.tvCategory.setText(report.getCategory());
        holder.tvAmount.setText(CurrencyUtil.formatCurrency(report.getAmount()));

        int progress = (int) report.getPercentage();
        holder.progressBar.setProgress(progress);

        // Set color based on percentage
        int colorRes;
        if (progress > 90) {
            colorRes = R.color.red_500;
        } else if (progress > 70) {
            colorRes = R.color.orange_500;
        } else {
            colorRes = R.color.green_500;
        }

        holder.progressBar.setProgressTintList(
                ContextCompat.getColorStateList(holder.itemView.getContext(), colorRes));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory;
        TextView tvAmount;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }
}