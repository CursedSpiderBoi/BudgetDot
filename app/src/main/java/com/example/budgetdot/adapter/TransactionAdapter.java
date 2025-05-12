package com.example.budgetdot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetdot.R;
import com.example.budgetdot.model.Transaction;
import com.example.budgetdot.util.CurrencyUtil;
import com.example.budgetdot.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactions = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvCategory, tvAmount, tvDate;
        private ImageView ivIcon;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvDate = itemView.findViewById(R.id.tv_date);
            ivIcon = itemView.findViewById(R.id.iv_icon);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(transactions.get(position));
                }
            });
        }

        public void bind(Transaction transaction) {
            tvTitle.setText(transaction.getTitle());
            tvCategory.setText(transaction.getCategory());
            tvAmount.setText(CurrencyUtil.formatCurrency(transaction.getAmount()));
            tvDate.setText(DateUtil.formatDate(transaction.getDate()));

            // Set icon based on category
            int iconRes = transaction.getType().equals("income")
                    ? R.drawable.ic_income
                    : R.drawable.ic_expense;
            ivIcon.setImageResource(iconRes);

            // Set color based on type
            int colorRes = transaction.getType().equals("income")
                    ? R.color.green_500
                    : R.color.red_500;
            tvAmount.setTextColor(itemView.getContext().getColor(colorRes));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Transaction transaction);
    }
}