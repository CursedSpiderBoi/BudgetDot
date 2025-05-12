package com.example.budgetdot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetdot.R;
import com.example.budgetdot.model.Budget;
import com.example.budgetdot.util.CurrencyUtil;

import java.util.ArrayList;
import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {

    private List<Budget> budgets = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_budget, parent, false);
        return new BudgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        Budget budget = budgets.get(position);
        holder.bind(budget);
    }

    @Override
    public int getItemCount() {
        return budgets.size();
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class BudgetViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategory, tvLimit, tvSpent, tvRemaining;
        private ProgressBar progressBar;

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvLimit = itemView.findViewById(R.id.tv_limit);
            tvSpent = itemView.findViewById(R.id.tv_spent);
            tvRemaining = itemView.findViewById(R.id.tv_remaining);
            progressBar = itemView.findViewById(R.id.progress_bar);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(budgets.get(position));
                }
            });
        }

        public void bind(Budget budget) {
            tvCategory.setText(budget.getCategory());
            tvLimit.setText(CurrencyUtil.formatCurrency(budget.getLimit()));
            tvSpent.setText(CurrencyUtil.formatCurrency(budget.getSpent()));

            double remaining = budget.getLimit() - budget.getSpent();
            tvRemaining.setText(CurrencyUtil.formatCurrency(remaining));

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
                    itemView.getContext().getResources().getColorStateList(colorRes));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Budget budget);
    }
}