package com.example.lab1grades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectGradeAdapter extends RecyclerView.Adapter<SubjectGradeAdapter.SubjectGradeViewHolder> {

    private String[] subjects;
    private String[] grades;

    public SubjectGradeAdapter(String[] subjects) {
        this.subjects = subjects;
        this.grades = new String[subjects.length]; // Inicjalizacja ocen dla każdego przedmiotu
    }

    @NonNull
    @Override
    public SubjectGradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject_grade, parent, false);
        return new SubjectGradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectGradeViewHolder holder, int position) {
        holder.textViewSubjectName.setText(subjects[position]);

        holder.radioGroupGrades.setOnCheckedChangeListener(null); // Reset listenera, by uniknąć niepotrzebnych wywołań zdarzeń

        // Ustawianie ocen dla danego przedmiotu
        if (grades[position] != null) {
            int radioButtonId = getRadioButtonIdFromGrade(grades[position]);
            holder.radioGroupGrades.check(radioButtonId);
        }

        // Obsługa zmiany oceny
        holder.radioGroupGrades.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedGrade = getGradeFromRadioButtonId(checkedId);
            grades[position] = selectedGrade;
        });
    }

    @Override
    public int getItemCount() {
        return subjects.length;
    }

    public static class SubjectGradeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSubjectName;
        RadioGroup radioGroupGrades;

        public SubjectGradeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSubjectName = itemView.findViewById(R.id.textViewSubjectName);
            radioGroupGrades = itemView.findViewById(R.id.radioGroupGrades);
        }
    }

    // Metoda pomocnicza do mapowania oceny na ID RadioButtona
    private int getRadioButtonIdFromGrade(String grade) {
        // Implementacja mapowania oceny na RadioButtonId
        // ...

        return 0; // Zwróć ID RadioButtona na podstawie oceny
    }

    // Metoda pomocnicza do mapowania ID RadioButtona na ocenę
    private String getGradeFromRadioButtonId(int checkedId) {
        // Implementacja mapowania RadioButtonId na ocenę
        // ...

        return ""; // Zwróć ocenę na podstawie ID RadioButtona
    }
}