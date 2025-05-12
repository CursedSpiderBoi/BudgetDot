package com.example.budgetdot.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    private static final String PREFS_NAME = "BudgetDotPrefs";
    private static final String KEY_CURRENCY = "currency";
    private static final String KEY_THEME = "theme";

    private final SharedPreferences preferences;

    public PreferencesHelper(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public String getCurrency() {
        return preferences.getString(KEY_CURRENCY, "USD");
    }

    public void setCurrency(String currencyCode) {
        preferences.edit().putString(KEY_CURRENCY, currencyCode).apply();
    }

    public String getTheme() {
        return preferences.getString(KEY_THEME, "system");
    }

    public void setTheme(String theme) {
        preferences.edit().putString(KEY_THEME, theme).apply();
    }
}