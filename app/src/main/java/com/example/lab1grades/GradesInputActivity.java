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

    private static final String SAVED_GRADES_KEY = "saved_grades";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_input);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.rvGrades);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        numberOfGrades = intent.getIntExtra("numberOfGrades", 0);

        if (numberOfGrades > 0) {
            String[] subjects = getResources().getStringArray(R.array.subjects_array);
            String[] limitedSubjects = new String[numberOfGrades];
            System.arraycopy(subjects, 0, limitedSubjects, 0, Math.min(subjects.length, numberOfGrades));

            if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_GRADES_KEY)) {
                // Przywrócenie danych adaptera
                String[] savedGrades = savedInstanceState.getStringArray(SAVED_GRADES_KEY);
                adapter = new SubjectGradeAdapter(limitedSubjects, savedGrades);
            } else {
                adapter = new SubjectGradeAdapter(limitedSubjects);
            }

            adapter.setGradeChangeListener(this);
            recyclerView.setAdapter(adapter);
        }

        btAverage = findViewById(R.id.btAvarage);
        btAverage.setOnClickListener(view -> calculateAndDisplayAverageGrade());
    }

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
        // Możesz reagować na zmiany ocen, jeśli to konieczne
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
