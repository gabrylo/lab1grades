package com.example.lab1grades;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectGradeAdapter extends RecyclerView.Adapter<SubjectGradeAdapter.SubjectGradeViewHolder> {

    private String[] subjects;
    private String[] grades;
    private GradeChangeListener gradeChangeListener;

    public SubjectGradeAdapter(String[] subjects) {
        this.subjects = subjects;
        this.grades = new String[subjects.length];
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

        holder.radioGroupGrades.setOnCheckedChangeListener(null);

        if (grades[position] != null) {
            int radioButtonId = getRadioButtonIdFromGrade(grades[position]);
            holder.radioGroupGrades.check(radioButtonId);
        }

        holder.radioGroupGrades.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedGrade = getGradeFromRadioButtonId(checkedId);
            grades[position] = selectedGrade;

            if (gradeChangeListener != null) {
                gradeChangeListener.onGradeChanged(grades);
            }
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

    private int getRadioButtonIdFromGrade(String grade) {
        switch (grade) {
            case "2":
                return R.id.radioButton2;
            case "3":
                return R.id.radioButton3;
            case "4":
                return R.id.radioButton4;
            case "5":
                return R.id.radioButton5;
            case "6":
                return R.id.radioButton6;
            default:
                return 0;
        }
    }

    private String getGradeFromRadioButtonId(int checkedId) {
        if (checkedId == R.id.radioButton2) {
            return "2";
        } else if (checkedId == R.id.radioButton3) {
            return "3";
        } else if (checkedId == R.id.radioButton4) {
            return "4";
        } else if (checkedId == R.id.radioButton5) {
            return "5";
        } else if (checkedId == R.id.radioButton6) {
            return "6";
        } else {
            return "";
        }
    }


    public String[] getGrades() {
        return grades;
    }

    public interface GradeChangeListener {
        void onGradeChanged(String[] updatedGrades);
    }

    public void setGradeChangeListener(GradeChangeListener listener) {
        this.gradeChangeListener = listener;
    }

    public boolean areAllGradesEntered() {
        for (String grade : grades) {
            if (grade == null || grade.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
