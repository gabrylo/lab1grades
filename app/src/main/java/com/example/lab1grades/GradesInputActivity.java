package com.example.lab1grades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class GradesInputActivity extends AppCompatActivity {

    private int numberOfGrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_input);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        numberOfGrades = intent.getIntExtra("numberOfGrades", 0); // Pobierz liczbę przedmiotów


        if (numberOfGrades > 0) {
            String[] subjects = getResources().getStringArray(R.array.subjects_array);

            // Ogranicz liczbę przedmiotów do numberOfGrades
            String[] limitedSubjects = new String[numberOfGrades];
            System.arraycopy(subjects, 0, limitedSubjects, 0, Math.min(subjects.length, numberOfGrades));

            SubjectGradeAdapter adapter = new SubjectGradeAdapter(limitedSubjects);
            recyclerView.setAdapter(adapter);
        }
    }
}