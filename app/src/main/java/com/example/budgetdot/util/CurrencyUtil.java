package com.example.budgetdot.util;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtil {
    public static String formatCurrency(double amount) {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(amount);
    }
}