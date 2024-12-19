package com.example.finance;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    private ExpenseDatabase expenseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        expenseDatabase = new ExpenseDatabase(this);

        LinearLayout chartLayout = findViewById(R.id.chartLayout);

        // Создание кастомного представления для диаграммы
        ChartView chartView = new ChartView(this);
        chartLayout.addView(chartView);
    }

    // Кастомное представление для рисования диаграммы
    public class ChartView extends View {

        public ChartView(ChartActivity context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(60);

            // Получаем расходы из базы данных
            List<String> expenses = expenseDatabase.getAllExpenses();
            HashMap<String, Float> categoryTotals = new HashMap<>();

            // Вычисляем суммарные расходы по категориям
            for (String expense : expenses) {
                String[] parts = expense.split(", ");
                String category = parts[2].split(": ")[1];
                float amount = Float.parseFloat(parts[0].split(": ")[1]);

                categoryTotals.put(category, categoryTotals.getOrDefault(category, 0f) + amount);
            }

            // Рисуем диаграмму
            float totalExpenses = 0;
            for (float amount : categoryTotals.values()) {
                totalExpenses += amount;
            }

            float left = 150;
            float top = 50;
            float width = 700;  // Увеличено для большего размера диаграммы
            float height = 700;

            float currentAngle = 0;
            int i = 0;

            // Рисуем сегменты диаграммы
            for (String category : categoryTotals.keySet()) {
                float categoryAmount = categoryTotals.get(category);
                float percentage = categoryAmount / totalExpenses;

                // Рисуем сегмент диаграммы
                paint.setColor(getCategoryColor(i));
                canvas.drawArc(left, top, left + width, top + height,
                        currentAngle, percentage * 360, true, paint);

                currentAngle += percentage * 360;
                i++;
            }

            // Рисуем легенду (цвета категорий с подписями)
            paint.setColor(Color.BLACK);
            float legendX = left;
            float legendY = top + height + 50;  // Расстояние от диаграммы до легенды
            float legendSpacing = 100;

            i = 0;
            for (String category : categoryTotals.keySet()) {
                float percentage = categoryTotals.get(category) / totalExpenses;
                String text = category + ": " + (int)(percentage * 100) + "%";

                // Рисуем прямоугольник для цвета категории
                paint.setColor(getCategoryColor(i));
                canvas.drawRect(legendX, legendY + i * legendSpacing, legendX + 45, legendY + 45 + i * legendSpacing, paint);

                // Рисуем текст рядом с прямоугольником
                paint.setColor(Color.BLACK);
                canvas.drawText(text, legendX + 50, legendY + 30 + i * legendSpacing, paint);

                i++;
            }
        }

        // Возвращаем цвет для каждой категории
        private int getCategoryColor(int index) {
            int[] colors = {
                    Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA,
                    Color.DKGRAY, Color.LTGRAY, Color.BLACK, Color.GRAY
            };
            return colors[index % colors.length];
        }
    }
}