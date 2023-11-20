package com.example.lab1grades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

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
                startActivity(intent);
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
}