package com.example.lab1grades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private EditText edName;
    private EditText surnameEditText;
    private EditText gradeNumberEditText;
    private Button btGrades;

    private boolean isNameValid = false;
    private boolean isSurnameValid = false;
    private boolean isGradesValid = false;

    private static final int REQUEST_CODE_GRADES_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        edName = findViewById(R.id.edName);
        surnameEditText = findViewById(R.id.edSurname);
        gradeNumberEditText = findViewById(R.id.edGradeNumber);
        btGrades = findViewById(R.id.btGrades);

        btGrades.setVisibility(View.INVISIBLE);

        setFocusListeners();
        setButtonClick();

        if (savedInstanceState != null) {
            isNameValid = savedInstanceState.getBoolean("nameValid");
            isSurnameValid = savedInstanceState.getBoolean("surnameValid");
            isGradesValid = savedInstanceState.getBoolean("gradesValid");
            validateAllFields();
        }
    }

    private void setFocusListeners() {
        edName.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                isNameValid = validateField(edName, getString(R.string.empty_field_error), false);
                validateAllFields();
            }
        });

        surnameEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                isSurnameValid = validateField(surnameEditText, getString(R.string.empty_field_error), false);
                validateAllFields();
            }
        });

        gradeNumberEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                isGradesValid = validateField(gradeNumberEditText, getString(R.string.empty_grade_error), true);
                validateAllFields();
            }
        });
    }

    private void setButtonClick() {
        btGrades.setOnClickListener(view -> onGradesButtonClick());
    }

    private boolean validateField(EditText editText, String errorMessage, boolean isGradeField) {
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) {
            editText.setError(errorMessage);
            Toast.makeText(getApplicationContext(), getString(R.string.empty_field_error), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (isGradeField) {
            try {
                int numberOfGrades = Integer.parseInt(text);
                if (numberOfGrades < 5 || numberOfGrades > 15) {
                    editText.setError(getString(R.string.grade_range_error));
                    Toast.makeText(getApplicationContext(), getString(R.string.grade_range_error), Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (NumberFormatException e) {
                editText.setError(getString(R.string.invalid_grade_error));
                return false;
            }
        }

        editText.setError(null);
        return true;
    }

    private void validateAllFields() {
        btGrades.setVisibility((isNameValid && isSurnameValid && isGradesValid) ? View.VISIBLE : View.INVISIBLE);
    }

    private void onGradesButtonClick() {
        if (isNameValid && isSurnameValid && isGradesValid) {
            int numberOfGrades = Integer.parseInt(gradeNumberEditText.getText().toString());
            if (numberOfGrades >= 5 && numberOfGrades <= 15) {
                Intent intent = new Intent(MainActivity.this, GradesInputActivity.class);
                intent.putExtra("numberOfGrades", numberOfGrades);
                startActivityForResult(intent, REQUEST_CODE_GRADES_INPUT);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.grade_range_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", edName.getText().toString());
        outState.putString("surname", surnameEditText.getText().toString());
        outState.putString("grades", gradeNumberEditText.getText().toString());
        outState.putBoolean("nameValid", isNameValid);
        outState.putBoolean("surnameValid", isSurnameValid);
        outState.putBoolean("gradesValid", isGradesValid);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        edName.setText(savedInstanceState.getString("name"));
        surnameEditText.setText(savedInstanceState.getString("surname"));
        gradeNumberEditText.setText(savedInstanceState.getString("grades"));
        isNameValid = savedInstanceState.getBoolean("nameValid");
        isSurnameValid = savedInstanceState.getBoolean("surnameValid");
        isGradesValid = savedInstanceState.getBoolean("gradesValid");
        validateAllFields();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GRADES_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                float averageGrade = data.getFloatExtra("averageGrade", 0.0f);
                String averageText = "Średnia ocen: " + String.format("%.2f", averageGrade);

                TextView tvGrades = findViewById(R.id.tvGrades);
                Button btGrades = findViewById(R.id.btGrades);

                if (averageGrade >= 3) {
                    tvGrades.setText(averageText); // Ustawienie tekstu w TextView
                    btGrades.setText("SUPER");
                    btGrades.setOnClickListener(view -> {
                        Toast.makeText(this, "Gratulacje! Otrzymujesz zaliczenie", Toast.LENGTH_SHORT).show();
                        finish(); // Zakończenie aplikacji po kliknięciu przycisku
                    });
                } else {
                    tvGrades.setText(averageText); // Ustawienie tekstu w TextView
                    btGrades.setText("Tym razem nie poszlo ): "); // Tekst pierwotny przycisku
                    btGrades.setOnClickListener(view -> {
                        Toast.makeText(this, "Wysyłam podanie o zaliczenie warunkowe", Toast.LENGTH_SHORT).show();
                        finish(); // Zakończenie aplikacji po kliknięciu przycisku
                    });
                }
            } else {
                Toast.makeText(this, "Nie udało się obliczyć średniej oceny", Toast.LENGTH_SHORT).show();
            }
        }
    }

}