package com.example.lab1grades;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edName;
    private EditText surnameEditText;
    private EditText gradeNumberEditText;
    private Button btGrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edName = findViewById(R.id.edName);
        surnameEditText = findViewById(R.id.edSurname);
        gradeNumberEditText = findViewById(R.id.edGradeNumber);
        btGrades = findViewById(R.id.btGrades);

        btGrades.setVisibility(View.INVISIBLE);

        edName.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                validateName(edName);
            }
        });

        surnameEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                validateName(surnameEditText);
            }
        });

        gradeNumberEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                validateGrades(gradeNumberEditText);
            }
        });

        btGrades.setOnClickListener(view -> onGradesButtonClick());
    }

    private void validateName(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) {
            editText.setError("Pole nie może być puste");
        }
    }

    private void validateGrades(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) {
            editText.setError("Pole nie może być puste");
            return;
        }

        try {
            int numberOfGrades = Integer.parseInt(text);
            if (numberOfGrades < 5 || numberOfGrades > 15) {
                editText.setError("Liczba ocen musi być z zakresu 5-15");
                return;
            }
            // Jeśli wszystko jest w porządku, pokaż przycisk
            btGrades.setVisibility(View.VISIBLE);
        } catch (NumberFormatException e) {
            editText.setError("Wprowadź poprawną liczbę");
        }
    }

    private void onGradesButtonClick() {
        // Tutaj dodaj logikę, która ma być wywołana po kliknięciu przycisku btGrades
        // Na przykład zapisanie ocen, obliczenia itp.
    }
}