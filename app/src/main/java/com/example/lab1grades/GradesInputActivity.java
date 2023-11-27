package com.example.lab1grades;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class GradesInputActivity extends AppCompatActivity {

    private int numberOfGrades;
    private Button btavarage;
    private SubjectGradeAdapter adapter; // Deklaracja adaptera

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_input);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        numberOfGrades = intent.getIntExtra("numberOfGrades", 0);

        if (numberOfGrades > 0) {
            String[] subjects = getResources().getStringArray(R.array.subjects_array);
            String[] limitedSubjects = new String[numberOfGrades];
            System.arraycopy(subjects, 0, limitedSubjects, 0, Math.min(subjects.length, numberOfGrades));

            // Inicjalizacja adaptera na poziomie klasy
            adapter = new SubjectGradeAdapter(limitedSubjects);
            recyclerView.setAdapter(adapter);
        }

        btavarage = findViewById(R.id.btavarage);
        btavarage.setOnClickListener(view -> {
            boolean allGradesEntered = true; // Flaga określająca, czy wszystkie oceny zostały wprowadzone

            if (adapter != null && !adapter.areAllGradesEntered()) {
                Toast.makeText(this, "Wprowadź wszystkie oceny", Toast.LENGTH_SHORT).show();
                return; // Przerwij działanie metody, jeśli nie wszystkie oceny zostały wprowadzone
            }

            calculateAndDisplayAverageGrade();
        });
    }

    private void calculateAndDisplayAverageGrade() {
        if (adapter != null) {
            String[] grades = adapter.getGrades();
            boolean allGradesEntered = true; // Flaga określająca, czy wszystkie oceny zostały wprowadzone

            if (grades != null) {
                for (String grade : grades) {
                    if (grade.isEmpty()) {
                        allGradesEntered = false;
                        break;
                    }
                }
            }

            if (allGradesEntered) {
                float averageGrade = calculateAverageGrade(grades);
                if (averageGrade > 0) {
                    String averageText = "Średnia ocen: " + String.format("%.2f", averageGrade);
                    Toast.makeText(this, averageText, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Brak ocen do obliczenia średniej", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Wprowadź wszystkie oceny", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private float calculateAverageGrade(String[] grades) {
        int sum = 0;
        int count = 0;

        for (String grade : grades) {
            if (!grade.isEmpty()) {
                sum += Integer.parseInt(grade);
                count++;
            }
        }

        if (count > 0) {
            return (float) sum / count;
        } else {
            return 0; // Brak ocen, zwróć 0
        }
    }
}