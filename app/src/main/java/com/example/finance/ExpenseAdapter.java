package com.example.finance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    public ExpenseAdapter(@NonNull Context context, @NonNull List<Expense> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expense, parent, false);
        }

        Expense expense = getItem(position);

        TextView amountTextView = convertView.findViewById(R.id.expenseAmountText);
        TextView categoryTextView = convertView.findViewById(R.id.expenseCategoryText);
        TextView dateTextView = convertView.findViewById(R.id.expenseDateText);

        if (expense != null) {
            amountTextView.setText(expense.getAmount());
            categoryTextView.setText(expense.getCategory());
            dateTextView.setText(expense.getDate());
        }

        return convertView;
    }
}