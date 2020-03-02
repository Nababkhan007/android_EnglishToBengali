package com.example.englishtobengali;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.CompoundButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private SwitchCompat switchCompat;
    private Locale myLocale;
    private String currentLanguage = "en", currentLang;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private long highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        getIntentValue();

        getSharedPrefValue();

        switchButtonStatus();

        switchButtonStatusChange();
    }

    private void getIntentValue() {
        currentLanguage = getIntent().getStringExtra(currentLang);
    }

    private void switchButtonStatusChange() {
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sharedPrefValue(1);
                    setLocale("bn", 0);

                } else {
                    sharedPrefValue(0);
                    setLocale("en", 0);
                }
            }
        });
    }

    private void switchButtonStatus() {
        if (highScore == 1) {
            switchCompat.setChecked(true);
            setLocale("bn", 0);

        } else {
            switchCompat.setChecked(false);
            setLocale("en", 0);
        }
    }

    private void getSharedPrefValue() {
        highScore = sharedPreferences.getInt("value", 0);
    }

    private void initialization() {
        switchCompat = findViewById(R.id.switchCompatTgBtnId);
        sharedPreferences = getSharedPreferences("languageState", Context.MODE_PRIVATE);
    }

    public void sharedPrefValue(int value) {
        editor = sharedPreferences.edit();
        editor.putInt("value", value);
        editor.apply();
    }

    public void setLocale(String localeName, int i) {
        if (1 == i) {
            return;
        }

        if (!localeName.equals(currentLanguage)) {
            myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            startActivity(new Intent(this, MainActivity.class)
                    .putExtra(currentLang, localeName));
            finish();

        } else {
//            Toast.makeText(MainActivity.this, "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }
}
