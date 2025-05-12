package com.example.budgetdot.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.budgetdot.R;
import com.example.budgetdot.util.PreferencesHelper;

public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    private PreferencesHelper prefs;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        prefs = new PreferencesHelper(requireContext());

        // Initialize summary values
        updatePreferenceSummary("theme");
        updatePreferenceSummary("currency");
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "theme":
                applyTheme(sharedPreferences.getString(key, "system"));
                updatePreferenceSummary(key);
                break;
            case "currency":
                updatePreferenceSummary(key);
                // Notify any listening activities to refresh currency displays
                if (getActivity() != null) {
                    getActivity().recreate();
                }
                break;
        }
    }

    private void applyTheme(String themeValue) {
        switch (themeValue) {
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

    private void updatePreferenceSummary(String key) {
        Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            CharSequence entry = listPreference.getEntry();
            if (entry != null) {
                preference.setSummary(entry);
            }
        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        // Handle any custom preference clicks here
        return super.onPreferenceTreeClick(preference);
    }
}