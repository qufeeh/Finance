package com.example.finance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ExpenseDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expenses.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_EXPENSES = "expenses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_DATE = "date";

    public ExpenseDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_EXPENSES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AMOUNT + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_DATE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }

    public void addExpense(String amount, String category, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_DATE, date);
        db.insert(TABLE_EXPENSES, null, values);
        db.close();
    }

    public List<String> getAllExpenses() {
        List<String> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSES, null);

        if (cursor.moveToFirst()) {
            do {
                String expense = "ID: " + cursor.getString(0) + // Добавляем ID
                        ", Сумма: " + cursor.getString(1) +
                        ", Категория: " + cursor.getString(2) +
                        ", Дата: " + cursor.getString(3);
                expenses.add(expense);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return expenses;
    }
    public List<String> getFilteredExpenses(String category, String date) {
        List<String> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Строим SQL-запрос с фильтрацией
        String query = "SELECT * FROM " + TABLE_EXPENSES + " WHERE " +
                COLUMN_CATEGORY + " LIKE ? AND " +
                COLUMN_DATE + " LIKE ?";

        Cursor cursor = db.rawQuery(query, new String[] { "%" + category + "%", "%" + date + "%" });

        if (cursor.moveToFirst()) {
            do {
                String expense = "Сумма: " + cursor.getString(1) +
                        ", Категория: " + cursor.getString(2) +
                        ", Дата: " + cursor.getString(3);
                expenses.add(expense);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return expenses;
    }

    public List<String> getFilteredExpensesByCategory(String category) {
        List<String> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Строим SQL-запрос для фильтрации только по категории
        String query = "SELECT * FROM " + TABLE_EXPENSES + " WHERE " +
                COLUMN_CATEGORY + " LIKE ?";

        Cursor cursor = db.rawQuery(query, new String[] { "%" + category + "%" });

        if (cursor.moveToFirst()) {
            do {
                String expense = "Сумма: " + cursor.getString(1) +
                        ", Категория: " + cursor.getString(2) +
                        ", Дата: " + cursor.getString(3);
                expenses.add(expense);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return expenses;
    }
    public void deleteExpense(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSES, COLUMN_ID + " = ?", new String[]{id});
        db.close();
    }

    public List<String> getFilteredExpensesByDate(String date) {
        List<String> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Строим SQL-запрос для фильтрации только по дате
        String query = "SELECT * FROM " + TABLE_EXPENSES + " WHERE " +
                COLUMN_DATE + " LIKE ?";

        Cursor cursor = db.rawQuery(query, new String[] { "%" + date + "%" });

        if (cursor.moveToFirst()) {
            do {
                String expense = "Сумма: " + cursor.getString(1) +
                        ", Категория: " + cursor.getString(2) +
                        ", Дата: " + cursor.getString(3);
                expenses.add(expense);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return expenses;
    }
}