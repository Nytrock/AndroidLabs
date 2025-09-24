package com.example.lab3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private String firstNumberRaw;
    private String secondNumberRaw;
    private String binaryOperation;
    private String unaryOperation;
    private boolean isOnSecondNumber;
    private boolean isCalculated;

    private TextView outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputTextView = findViewById(R.id.output_view);
        clearAll();

        findViewById(R.id.button_one).setOnClickListener(view -> placeNumber('1'));
        findViewById(R.id.button_two).setOnClickListener(view -> placeNumber('2'));
        findViewById(R.id.button_three).setOnClickListener(view -> placeNumber('3'));
        findViewById(R.id.button_four).setOnClickListener(view -> placeNumber('4'));
        findViewById(R.id.button_five).setOnClickListener(view -> placeNumber('5'));
        findViewById(R.id.button_six).setOnClickListener(view -> placeNumber('6'));
        findViewById(R.id.button_seven).setOnClickListener(view -> placeNumber('7'));
        findViewById(R.id.button_eight).setOnClickListener(view -> placeNumber('8'));
        findViewById(R.id.button_nine).setOnClickListener(view -> placeNumber('9'));
        findViewById(R.id.button_zero).setOnClickListener(view -> placeNumber('0'));

        findViewById(R.id.button_plus).setOnClickListener(view -> placeBinaryOperation("+"));
        findViewById(R.id.button_minus).setOnClickListener(view -> placeBinaryOperation("-"));
        findViewById(R.id.button_div).setOnClickListener(view -> placeBinaryOperation("/"));
        findViewById(R.id.button_mult).setOnClickListener(view -> placeBinaryOperation("*"));
        findViewById(R.id.button_sin).setOnClickListener(view -> placeUnaryOperation("sin"));
        findViewById(R.id.button_cos).setOnClickListener(view -> placeUnaryOperation("cos"));
        findViewById(R.id.button_tan).setOnClickListener(view -> placeUnaryOperation("tan"));
        findViewById(R.id.button_sqrt).setOnClickListener(view -> placeUnaryOperation("sqrt"));

        findViewById(R.id.button_clear_number).setOnClickListener(view -> clearNumber());
        findViewById(R.id.button_clear_all).setOnClickListener(view -> clearAll());
        findViewById(R.id.button_remove).setOnClickListener(view -> clearSymbol());

        findViewById(R.id.button_sign).setOnClickListener(view -> inverseNumber());
        findViewById(R.id.button_dot).setOnClickListener(view -> placeDot());
        findViewById(R.id.button_equal).setOnClickListener(view -> calculateResult());
    }

    private void placeNumber(char symbol) {
        if (isCalculated) {
            isCalculated = false;
            clearNumber();
        }

        String nowNumber = getCurrentNumberRaw();
        if (Objects.equals(nowNumber, "0"))
            nowNumber = Character.toString(symbol);
        else
            nowNumber += symbol;

        setCurrentNumberRaw(nowNumber);
    }

    private void placeDot() {
        String nowNumber = getCurrentNumberRaw();
        if (isCalculated || nowNumber.isEmpty()) {
            setCurrentNumberRaw("0.");
            return;
        }

        if (nowNumber.contains("."))
            return;

        setCurrentNumberRaw(nowNumber + '.');
    }

    private void placeBinaryOperation(String operationToPlace) {
        if (isOnSecondNumber && !secondNumberRaw.isEmpty())
            calculateResult();
        else if (firstNumberRaw.isEmpty())
            setCurrentNumberRaw("0");

        isOnSecondNumber = true;
        isCalculated = false;
        binaryOperation = operationToPlace;
        updateOutputText();
    }

    private void placeUnaryOperation(String operationToPlace){
        unaryOperation = operationToPlace;
        calculateResult();
        updateOutputText();
    }

    private void clearAll() {
        firstNumberRaw = "";
        secondNumberRaw = "";
        binaryOperation = "";
        unaryOperation = "";
        isOnSecondNumber = false;
        isCalculated = false;
        updateOutputText();
    }

    private void clearNumber() {
        if (getCurrentNumberRaw().isEmpty())
            return;

        setCurrentNumberRaw("");
    }

    private void clearSymbol() {
        if (isCalculated)
            return;

        String nowNumber = getCurrentNumberRaw();
        if (nowNumber.isEmpty())
            return;

        nowNumber = nowNumber.substring(0, nowNumber.length() - 1);
        setCurrentNumberRaw(nowNumber);
    }

    private void inverseNumber() {
        String nowNumber = getCurrentNumberRaw();
        if (nowNumber.isEmpty())
            return;

        if (nowNumber.charAt(0) == '-')
            nowNumber = nowNumber.substring(1);
        else
            nowNumber = '-' + nowNumber;
        setCurrentNumberRaw(nowNumber);
    }

    private void calculateResult() {
        double firstNumber, secondNumber, result;
        firstNumber = getNumberFromRawVersion(firstNumberRaw);
        if (secondNumberRaw.isEmpty())
            secondNumber = firstNumber;
        else
            secondNumber = getNumberFromRawVersion(secondNumberRaw);

        double nowNumber = firstNumber;
        if (isOnSecondNumber)
            nowNumber = secondNumber;

        switch (unaryOperation){
            case "sin":
                nowNumber = Math.sin(nowNumber);
                break;
            case "cos":
                nowNumber = Math.cos(nowNumber);
                break;
            case "tan":
                nowNumber = Math.tan(nowNumber);
                break;
            case "sqrt":
                nowNumber = Math.sqrt(nowNumber);
                break;
        }

        if (isOnSecondNumber)
            secondNumber = nowNumber;
        else
            firstNumber = nowNumber;
        result = firstNumber;

        switch (binaryOperation) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "/":
                if (secondNumber == 0){
                    throwError();
                    return;
                }
                result = firstNumber / secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
        }

        clearAll();
        isCalculated = true;

        if (result % 1 != 0)
            firstNumberRaw = Double.toString(result);
        else
            firstNumberRaw = Integer.toString((int)Math.floor(result));
        updateOutputText();
    }

    private void throwError() {
        clearAll();
        outputTextView.setText(R.string.error_text);
    }

    private void updateOutputText() {
        String outputText = firstNumberRaw + binaryOperation + secondNumberRaw;
        outputTextView.setText(outputText);
    }

    private String getCurrentNumberRaw(){
        if (isOnSecondNumber)
            return secondNumberRaw;
        return firstNumberRaw;
    }

    private void setCurrentNumberRaw(String number){
        if (number.isEmpty())
            number = "0";

        if (isOnSecondNumber)
            secondNumberRaw = number;
        else
            firstNumberRaw = number;
        updateOutputText();
    }

    private double getNumberFromRawVersion(String rawNumber){
        if (rawNumber.isEmpty())
            return 0;
        return Double.parseDouble(rawNumber);
    }
}