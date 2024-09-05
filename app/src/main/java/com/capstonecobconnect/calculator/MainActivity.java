package com.capstonecobconnect.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private double firstNumber = Double.NaN;
    private double secondNumber = Double.NaN;
    private boolean operatorSelected = false;
    private char currentOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.editText);
    }
    public void onNumberClick(View view) {
        Button button = (Button) view;
        String number = button.getText().toString();

        display.append(number);
    }
    public void onOperatorClick(View view) {
        Button button = (Button) view;
        char newOperator = button.getText().toString().charAt(0);

        if (operatorSelected && !Double.isNaN(firstNumber)) {
            secondNumber = Double.parseDouble(display.getText().toString());
            double result = calculate(firstNumber, secondNumber, currentOperator);

            display.setText(String.format("%.2f", result));
            firstNumber = result;
            secondNumber = Double.NaN;
        } else {
            firstNumber = Double.parseDouble(display.getText().toString());
        }
        currentOperator = newOperator;
        operatorSelected = true;

        display.setText("");
    }
    public void onEqualClick(View view) {
        if (operatorSelected && !Double.isNaN(firstNumber)) {
            if (Double.isNaN(secondNumber)) {
                secondNumber = Double.parseDouble(display.getText().toString());
            }
            double result = calculate(firstNumber, secondNumber, currentOperator);
            display.setText(String.format("%.2f", result));
            reset();
        } else {
            display.setError("Incomplete input.");
        }
    }
    public void onClearClick(View view) {
        reset();
        display.setText("");
    }
    private double calculate(double num1, double num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2;
            default:
                return 0;
        }
    }
    private void reset() {
        firstNumber = Double.NaN;
        secondNumber = Double.NaN;
        operatorSelected = false;
        currentOperator = ' ';
    }
}
