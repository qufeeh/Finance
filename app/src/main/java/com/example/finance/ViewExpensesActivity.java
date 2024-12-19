package com.example.finance;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;


import java.util.List;

public class ViewExpensesActivity extends AppCompatActivity {

    private ListView expensesListView;
    private Button backButton;
    private Button filterButton;
    private Button deleteExpenseButton;
    private EditText filterCategoryEditText;
    private EditText filterDateEditText;
    private ExpenseDatabase expenseDatabase;
    private String selectedExpenseId; // или другой уникальный идентификатор


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);

        expenseDatabase = new ExpenseDatabase(this);

        expensesListView = findViewById(R.id.expensesListView);
        backButton = findViewById(R.id.backButton);
        filterButton = findViewById(R.id.filterButton);
        deleteExpenseButton = findViewById(R.id.deleteExpenseButton); // Инициализация кнопки
        filterCategoryEditText = findViewById(R.id.filterCategoryEditText);
        filterDateEditText = findViewById(R.id.filterDateEditText);

        // Изначально показываем все расходы
        displayExpenses(null, null);

        expensesListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedExpense = (String) parent.getItemAtPosition(position);
            selectedExpenseId = getExpenseIdFromString(selectedExpense); // Метод для извлечения ID
            // Показать кнопку "Удалить расход"
            deleteExpenseButton.setVisibility(View.VISIBLE);
        });

        deleteExpenseButton.setOnClickListener(v -> {
            if (selectedExpenseId != null) {
                expenseDatabase.deleteExpense(selectedExpenseId);
                displayExpenses(null, null); // Обновить список
                deleteExpenseButton.setVisibility(View.GONE); // Скрыть кнопку
            }
        });

        filterButton.setOnClickListener(v -> {
            String categoryFilter = filterCategoryEditText.getText().toString();
            String dateFilter = filterDateEditText.getText().toString();

            displayExpenses(categoryFilter, dateFilter);
        });

        expensesListView = findViewById(R.id.expensesListView);
        backButton = findViewById(R.id.backButton);

        List<String> expenses = expenseDatabase.getAllExpenses();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expenses);
        expensesListView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());
    }
    private String getExpenseIdFromString(String expense) {
        // Предположим, что ID хранится в строке, например, "ID: 1, Сумма: 100"
        return expense.split(", ")[0].split(": ")[1]; // Извлечение ID
    }
    // Метод для отображения расходов с фильтрами
    private void displayExpenses(String categoryFilter, String dateFilter) {
        List<String> expenses;

        if (categoryFilter != null && !categoryFilter.isEmpty() && dateFilter != null && !dateFilter.isEmpty()) {
            expenses = expenseDatabase.getFilteredExpenses(categoryFilter, dateFilter);
        } else if (categoryFilter != null && !categoryFilter.isEmpty()) {
            expenses = expenseDatabase.getFilteredExpensesByCategory(categoryFilter);
        } else if (dateFilter != null && !dateFilter.isEmpty()) {
            expenses = expenseDatabase.getFilteredExpensesByDate(dateFilter);
        } else {
            expenses = expenseDatabase.getAllExpenses();
        }

        // Обновить адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expenses);
        expensesListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayExpenses(null, null); // Перезагружаем данные при возврате
    }

}