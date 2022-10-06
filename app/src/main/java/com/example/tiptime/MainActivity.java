package com.example.tiptime;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.tiptime.databinding.ActivityMainBinding;

import java.text.NumberFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.calculateButton.setOnClickListener(view -> calculateTip());

        binding.costOfServiceEditText.setOnKeyListener((view, i, keyEvent) -> handleKeyEvent(view, i));
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    protected void calculateTip() {
        String stringInTextField = Objects.requireNonNull(binding.costOfServiceEditText.getText()).toString();
        double cost = Double.parseDouble(stringInTextField);
        int selectedId = binding.tipOptions.getCheckedRadioButtonId();
        boolean roundUp = binding.roundUpSwitch.isChecked();

        double tipPercentage;
        switch (selectedId) {
            case R.id.option_twenty_percent:
                tipPercentage = 0.2;
                break;
            case R.id.option_eightteen_percent:
                tipPercentage = 0.18;
                break;
            default:
                tipPercentage = 0.15;
                break;
        }
        Double tip = cost * tipPercentage;

        if (roundUp) {
            tip = Math.ceil(tip);
        }

        String formattedTip = NumberFormat.getCurrencyInstance().format(tip);
        binding.tipResult.setText("Tip Amount: " + formattedTip);
    }

    protected Boolean handleKeyEvent(View view, Integer keyCode) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        }
        return false;
    }
}