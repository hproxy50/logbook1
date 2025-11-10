package com.example.caculator_logbook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView result, expression;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0,
            btnPlus, btnMinus, btnMulti, btnDivi, btnR, btnEqual;

    String currentInput = "";
    double operand1 = 0;
    String operator = "";
    StringBuilder expressionBuilder = new StringBuilder();
    boolean isSecondOperand = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        expression = findViewById(R.id.expression);

        btn0 = findViewById(R.id.button0);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btn6 = findViewById(R.id.button6);
        btn7 = findViewById(R.id.button7);
        btn8 = findViewById(R.id.button8);
        btn9 = findViewById(R.id.button9);

        btnPlus = findViewById(R.id.buttonPlus);
        btnMinus = findViewById(R.id.buttonMinus);
        btnMulti = findViewById(R.id.buttonMulti);
        btnDivi = findViewById(R.id.buttonDivi);

        btnR = findViewById(R.id.buttonR);
        btnEqual = findViewById(R.id.buttonEqual);

        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                currentInput += b.getText().toString();
                expressionBuilder.append(b.getText().toString());
                expression.setText(expressionBuilder.toString());
            }
        };

        btn0.setOnClickListener(numberClickListener);
        btn1.setOnClickListener(numberClickListener);
        btn2.setOnClickListener(numberClickListener);
        btn3.setOnClickListener(numberClickListener);
        btn4.setOnClickListener(numberClickListener);
        btn5.setOnClickListener(numberClickListener);
        btn6.setOnClickListener(numberClickListener);
        btn7.setOnClickListener(numberClickListener);
        btn8.setOnClickListener(numberClickListener);
        btn9.setOnClickListener(numberClickListener);

        btnPlus.setOnClickListener(v -> handleOperator("+"));
        btnMinus.setOnClickListener(v -> handleOperator("-"));
        btnMulti.setOnClickListener(v -> handleOperator("*"));
        btnDivi.setOnClickListener(v -> handleOperator("/"));

        btnEqual.setOnClickListener(v -> calculateResult());

        btnR.setOnClickListener(v -> {
            currentInput = "";
            operand1 = 0;
            operator = "";
            isSecondOperand = false;
            result.setText("0");
            expression.setText("");
            expressionBuilder.setLength(0);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void handleOperator(String op) {
        if (!currentInput.isEmpty()) {
            operand1 = Double.parseDouble(currentInput);
            operator = op;
            expressionBuilder.append(op);
            currentInput = "";
            result.setText("0");
            expression.setText(expressionBuilder.toString());
        }
    }

    private void calculateResult() {
        if (operator.isEmpty() || currentInput.isEmpty()) return;

        double operand2 = Double.parseDouble(currentInput);
        double resultValue = 0;

        switch (operator) {
            case "+": resultValue = operand1 + operand2; break;
            case "-": resultValue = operand1 - operand2; break;
            case "*": resultValue = operand1 * operand2; break;
            case "/":
                if (operand2 == 0) {
                    result.setText("Cannot divide by 0");
                    expression.setText("");
                    currentInput = "";
                    expressionBuilder.setLength(0);
                    return;
                } else {
                    resultValue = operand1 / operand2;
                }
                break;
        }

        String finalResult = removeTrailingZero(resultValue);
        result.setText(finalResult);
        expression.setText("");
        currentInput = finalResult;
        expressionBuilder.setLength(0);
        operator = "";
        isSecondOperand = false;
    }

    private String removeTrailingZero(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.format("%s", value);
        }
    }
}
