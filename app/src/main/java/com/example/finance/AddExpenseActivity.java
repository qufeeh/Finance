package com.example.finance;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.content.Intent;


public class AddExpenseActivity extends AppCompatActivity {

    private EditText expenseAmount;
    private EditText expenseDate;
    private Spinner expenseCategorySpinner;
    private Button saveExpenseButton;

    // Заранее определенные категории
    private String[] categories = {"Food", "Technique", "Transport", "Internet", "Utilities", "Health", "Shopping", "Other"};

    private ExpenseDatabase expenseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        expenseDatabase = new ExpenseDatabase(this);

        expenseAmount = findViewById(R.id.expenseAmount);
        expenseCategorySpinner = findViewById(R.id.expenseCategorySpinner);
        expenseDate = findViewById(R.id.expenseDate);
        saveExpenseButton = findViewById(R.id.saveExpenseButton);
        // Настройка адаптера для Spinner
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseCategorySpinner.setAdapter(categoryAdapter);

        saveExpenseButton.setOnClickListener(v -> {
            String amount = expenseAmount.getText().toString();
            String category = expenseCategorySpinner.getSelectedItem().toString(); // Получаем выбранную категорию
            String date = expenseDate.getText().toString();

            if (!amount.isEmpty() && !category.isEmpty() && !date.isEmpty()) {
                expenseDatabase.addExpense(amount, category, date);
                Toast.makeText(AddExpenseActivity.this, "Трата сохранена!", Toast.LENGTH_SHORT).show();

                // Открыть MainActivity и очистить стек активностей
                Intent intent = new Intent(AddExpenseActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Очистить стек активностей
                startActivity(intent);
                finish();  // Закрыть текущую активность
            } else {
                Toast.makeText(AddExpenseActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });
    }
}