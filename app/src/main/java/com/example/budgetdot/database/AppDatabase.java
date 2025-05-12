package com.example.budgetdot.database;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.budgetdot.model.Transaction;
import com.example.budgetdot.model.Budget;
//import com.example.model.Category;
import com.example.budgetdot.model.Category;
//import org.tensorflow.lite.support.label.Category;

@Database(entities = {Transaction.class, Budget.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract TransactionDao transactionDao();
    public abstract BudgetDao budgetDao();
    public abstract CategoryDao categoryDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "budgetdot_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}