package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText display;
    private String currentDisplay = "";
    private String currentNumber = "";
    private double firstNumber = Double.NaN;
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                String buttonText = button.getText().toString();

                switch (buttonText) {
                    case "C":
                        clear();
                        break;
                    case "=":
                        calculate();
                        break;
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                        if (!Double.isNaN(firstNumber)) {
                            calculate();
                        }
                        operator = buttonText;
                        firstNumber = Double.parseDouble(currentNumber);
                        currentNumber = "";
                        currentDisplay += " " + operator + " ";
                        display.setText(currentDisplay);
                        break;
                    default:
                        currentNumber += buttonText;
                        currentDisplay += buttonText;
                        display.setText(currentDisplay);
                        break;
                }
            }
        };

        int[] buttonIDs = new int[]{
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonDot, R.id.buttonPlus, R.id.buttonMinus, R.id.buttonMultiply,
                R.id.buttonDivide, R.id.buttonEquals, R.id.buttonClear
        };

        for (int id : buttonIDs) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void clear() {
        currentNumber = "";
        currentDisplay = "";
        firstNumber = Double.NaN;
        operator = "";
        display.setText("");
    }

    private void calculate() {
        if (operator.isEmpty() || currentNumber.isEmpty()) {
            return;
        }

        double secondNumber = Double.parseDouble(currentNumber);
        double result = 0;

        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    display.setText("Error");
                    return;
                }
                break;
        }

        if (result == (long) result) {
            display.setText(String.format("%d", (long) result));
        } else {
            display.setText(String.format("%s", result));
        }

        currentNumber = String.valueOf(result);
        currentDisplay = currentNumber;
        operator = "";
        firstNumber = Double.NaN;
    }
}

