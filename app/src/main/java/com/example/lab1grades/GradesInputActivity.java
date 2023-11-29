package com.example.lab1grades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class GradesInputActivity extends AppCompatActivity implements SubjectGradeAdapter.GradeChangeListener {

    private int numberOfGrades;
    private Button btAverage;
    private SubjectGradeAdapter adapter;

    private static final String SAVED_GRADES_KEY = "saved_grades"; //klucz do zapisania ocen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_input);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Ustawienie przycisku powrotu

        RecyclerView recyclerView = findViewById(R.id.rvGrades);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        numberOfGrades = intent.getIntExtra("numberOfGrades", 0); // Pobranie liczby ocen z poprzedniej aktywności


        if (numberOfGrades > 0) {
            String[] subjects = getResources().getStringArray(R.array.subjects_array); // Pobranie tablicy przedmiotów z zasobów
            String[] limitedSubjects = new String[numberOfGrades];
            System.arraycopy(subjects, 0, limitedSubjects, 0, Math.min(subjects.length, numberOfGrades)); // Ograniczenie liczby przedmiotów

            // Sprawdzenie, czy istnieje zapisany stan adaptera
            if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_GRADES_KEY)) {
                // Przywrócenie stanu adaptera z zapisanych ocen
                String[] savedGrades = savedInstanceState.getStringArray(SAVED_GRADES_KEY);
                adapter = new SubjectGradeAdapter(limitedSubjects, savedGrades);
            } else {
                adapter = new SubjectGradeAdapter(limitedSubjects); // Utworzenie nowego adaptera
            }

            adapter.setGradeChangeListener(this); // Ustawienie nasłuchiwacza na zmiany ocen
            recyclerView.setAdapter(adapter); // Ustawienie adaptera w RecyclerView
        }

        btAverage = findViewById(R.id.btAvarage); // Inicjalizacja przycisku średniej
        btAverage.setOnClickListener(view -> calculateAndDisplayAverageGrade()); // Ustawienie akcji dla przycisku średniej
    }
    // Metoda obliczajaca i wyświetlajaca srednia ocene
    private void calculateAndDisplayAverageGrade() {
        if (adapter != null) {
            String[] grades = adapter.getGrades();
            boolean allGradesEntered = true;

            for (String grade : grades) {
                if (grade == null || grade.isEmpty()) {
                    allGradesEntered = false;
                    break;
                }
            }

            if (allGradesEntered) {
                float averageGrade = calculateAverageGrade(grades);
                if (averageGrade > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("averageGrade", averageGrade);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(this, R.string.no_grade_to_calc, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, R.string.enter_all_rating, Toast.LENGTH_SHORT).show();
            }
        }
    }
    // Metoda obliczajaca srednią ocene
    private float calculateAverageGrade(String[] grades) {
        int sum = 0;
        int count = 0;

        for (String grade : grades) {
            if (!grade.isEmpty()) {
                sum += Integer.parseInt(grade);
                count++;
            }
        }

        return count > 0 ? (float) sum / count : 0;
    }

    @Override
    public void onGradeChanged(String[] updatedGrades) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adapter != null) {
            String[] grades = adapter.getGrades();
            outState.putStringArray(SAVED_GRADES_KEY, grades);
        }
    }
}
