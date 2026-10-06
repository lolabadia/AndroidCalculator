package eetac.dsa.androidcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;

    private double firstNumber = 0;
    private String pendingOperation = "";
    private boolean newNumber = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        int[] numberButtons = {
                R.id.btn0,
                R.id.btn1,
                R.id.btn2,
                R.id.btn3,
                R.id.btn4,
                R.id.btn5,
                R.id.btn6,
                R.id.btn7,
                R.id.btn8,
                R.id.btn9
        };

        View.OnClickListener numberListener = view -> {
            Button button = (Button) view;
            String number = button.getText().toString();

            if (newNumber || display.getText().toString().equals("0")) {
                display.setText(number);
                newNumber = false;
            } else {
                display.append(number);
            }
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(numberListener);
        }

        findViewById(R.id.btnAdd).setOnClickListener(view -> {
            selectOperation("+");
        });

        findViewById(R.id.btnSubtract).setOnClickListener(view -> {
            selectOperation("-");
        });

        findViewById(R.id.btnMultiply).setOnClickListener(view -> {
            selectOperation("*");
        });

        findViewById(R.id.btnDivide).setOnClickListener(view -> {
            selectOperation("/");
        });

        findViewById(R.id.btnEquals).setOnClickListener(view -> {
            calculateResult();
        });

        findViewById(R.id.btnClear).setOnClickListener(view -> {
            clearCalculator();
        });

        findViewById(R.id.btnSin).setOnClickListener(view -> {
            calculateTrigonometric("sin");
        });

        findViewById(R.id.btnCos).setOnClickListener(view -> {
            calculateTrigonometric("cos");
        });

        findViewById(R.id.btnTan).setOnClickListener(view -> {
            calculateTrigonometric("tan");
        });
    }

    private void selectOperation(String operation) {

        if (!pendingOperation.isEmpty()) {
            calculateResult();
        }

        firstNumber = getDisplayValue();
        pendingOperation = operation;
        newNumber = true;
    }

    private void calculateResult() {

        if (pendingOperation.isEmpty()) {
            return;
        }

        double secondNumber = getDisplayValue();
        double result = 0;

        switch (pendingOperation) {

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
                if (secondNumber == 0) {
                    display.setText("Error");
                    pendingOperation = "";
                    newNumber = true;
                    return;
                }

                result = firstNumber / secondNumber;
                break;
        }

        showResult(result);

        firstNumber = result;
        pendingOperation = "";
        newNumber = true;
    }

    private void calculateTrigonometric(String operation) {

        double value = getDisplayValue();

        RadioButton radioDegrees = findViewById(R.id.radioDegrees);

        if (radioDegrees.isChecked()) {
            value = Math.toRadians(value);
        }

        double result = 0;

        switch (operation) {

            case "sin":
                result = Math.sin(value);
                break;

            case "cos":
                result = Math.cos(value);
                break;

            case "tan":
                result = Math.tan(value);
                break;
        }

        showResult(result);
        newNumber = true;
    }

    private double getDisplayValue() {

        try {
            return Double.parseDouble(display.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void showResult(double result) {

        if (result == (long) result) {
            display.setText(String.valueOf((long) result));
        } else {
            display.setText(String.valueOf(result));
        }
    }

    private void clearCalculator() {

        display.setText("0");
        firstNumber = 0;
        pendingOperation = "";
        newNumber = true;
    }
}