package com.example.lab1grades;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edName;
    private EditText surnameEditText;
    private EditText gradeNumberEditText;
    private Button btGrades;

    private boolean isNameValid = false;
    private boolean isSurnameValid = false;
    private boolean isGradesValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edName = findViewById(R.id.edName);
        surnameEditText = findViewById(R.id.edSurname);
        gradeNumberEditText = findViewById(R.id.edGradeNumber);
        btGrades = findViewById(R.id.btGrades);

        btGrades.setVisibility(View.INVISIBLE);

        setFocusListeners();
        setButtonClick();
    }

    private void setFocusListeners() {
        edName.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                isNameValid = validateName(edName);
                validateAllFields();
            }
        });

        surnameEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                isSurnameValid = validateName(surnameEditText);
                validateAllFields();
            }
        });

        gradeNumberEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                isGradesValid = validateGrades(gradeNumberEditText);
                validateAllFields();
            }
        });
    }

    private void setButtonClick() {
        btGrades.setOnClickListener(view -> onGradesButtonClick());
    }

    private void validateAllFields() {
        if (isNameValid && isSurnameValid && isGradesValid) {
            btGrades.setVisibility(View.VISIBLE);
        } else {
            btGrades.setVisibility(View.INVISIBLE);
        }
    }

    private boolean validateName(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) {
            editText.setError("Pole nie może być puste");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    private boolean validateGrades(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) {
            editText.setError("Pole nie może być puste");
            return false;
        }

        try {
            int numberOfGrades = Integer.parseInt(text);
            if (numberOfGrades < 5 || numberOfGrades > 15) {
                editText.setError("Liczba ocen musi być z zakresu 5-15");
                return false;
            } else {
                editText.setError(null);
                return true;
            }
        } catch (NumberFormatException e) {
            editText.setError("Wprowadź poprawną liczbę");
            return false;
        }
    }

    private void onGradesButtonClick() {
        // Tutaj dodaj logikę, która ma być wywołana po kliknięciu przycisku btGrades
        // Na przykład zapisanie ocen, obliczenia itp.
    }
}

