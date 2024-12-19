// Application for expense tracking in Android Studio
// Path: src/main/java/com/example/expensetracker/MainActivity.java

package com.example.finance;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addExpenseButton = findViewById(R.id.addExpenseButton);
        Button viewExpensesButton = findViewById(R.id.viewExpensesButton);
        Button viewChartButton = findViewById(R.id.viewChartButton);
        viewChartButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChartActivity.class);
            startActivity(intent);
        });

        addExpenseButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        viewExpensesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewExpensesActivity.class);
            startActivity(intent);
        });
    }
}