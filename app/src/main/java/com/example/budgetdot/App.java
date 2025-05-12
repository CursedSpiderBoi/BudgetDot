package com.example.budgetdot;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.budgetdot.database.AppDatabase;
import com.example.budgetdot.util.PreferencesHelper;

public class App extends Application {
    private static App instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = AppDatabase.getInstance(this);

        // Apply theme from preferences
        PreferencesHelper prefs = new PreferencesHelper(this);
        applyTheme(prefs.getTheme());
    }

    private void applyTheme(String theme) {
        switch (theme) {
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}