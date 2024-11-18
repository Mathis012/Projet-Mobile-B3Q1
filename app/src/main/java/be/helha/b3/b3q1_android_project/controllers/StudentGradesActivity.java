package be.helha.b3.b3q1_android_project.controllers;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.models.Evaluation;
import be.helha.b3.b3q1_android_project.models.EvaluationLab;

public class StudentGradesActivity extends AppCompatActivity {

    private AppDatabaseHelper dbHelper;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_grades);

        dbHelper = new AppDatabaseHelper(this);
        studentId = getIntent().getStringExtra("STUDENT_ID");

        loadEvaluationsForStudent();
    }

    private void loadEvaluationsForStudent() {
        LinearLayout gradesLayout = findViewById(R.id.gradesList);
        gradesLayout.removeAllViews();
        List<Evaluation> evaluations = getEvaluations();

        for (Evaluation evaluation : evaluations) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            if (evaluation.isSubEvaluation()) {
                row.setPadding(50, 8, 8, 8);
            } else {
                row.setPadding(0, 8, 8, 8);
            }

            TextView evalName = new TextView(this);
            evalName.setText(evaluation.getName());
            evalName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            TextView scoreDisplay = new TextView(this);
            scoreDisplay.setText(String.format("0 / %d", evaluation.getMaxPoint()));
            scoreDisplay.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            scoreDisplay.setGravity(TextView.TEXT_ALIGNMENT_CENTER);

            EditText scoreInput = new EditText(this);
            scoreInput.setHint("0");
            scoreInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            scoreInput.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            scoreInput.setMaxLines(1);
            scoreInput.setEms(3);

            Button saveButton = new Button(this);
            saveButton.setText("Save");
            saveButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            saveButton.setOnClickListener(v -> {
                String enteredScore = scoreInput.getText().toString();
                if (!enteredScore.isEmpty()) {
                    int score = Integer.parseInt(enteredScore);
                    scoreDisplay.setText(String.format("%d / %d", score, evaluation.getMaxPoint()));
                    saveGrade(evaluation.getId().toString(), enteredScore);
                }
            });

            row.addView(evalName);
            row.addView(scoreDisplay);
            row.addView(scoreInput);
            row.addView(saveButton);

            gradesLayout.addView(row);
        }
    }

    private void saveGrade(String evaluationId, String score) {
        UUID gradeId = UUID.randomUUID();
        dbHelper.getWritableDatabase().execSQL("INSERT INTO " + AppDbSchema.GradeTable.NAME + " (" +
                        AppDbSchema.GradeTable.Cols.UUID + ", " +
                        AppDbSchema.GradeTable.Cols.STUDENT_ID + ", " +
                        AppDbSchema.GradeTable.Cols.EVALUATION_ID + ", " +
                        AppDbSchema.GradeTable.Cols.SCORE + ") VALUES (?, ?, ?, ?)",
                new Object[]{gradeId.toString(), studentId, evaluationId, Integer.parseInt(score)});
        Log.d("StudentGradesActivity", "Grade saved for evaluation: " + evaluationId);
    }

    private List<Evaluation> getEvaluations() {
        List<Evaluation> evaluations = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                AppDbSchema.EvaluationTable.NAME,
                null, // Récupérer toutes les colonnes
                null, // Pas de filtre pour le moment
                null,
                null, null, null
        );

        try {
            while (cursor.moveToNext()) {
                String uuidString = cursor.getString(cursor.getColumnIndex(AppDbSchema.EvaluationTable.Cols.UUID));
                String name = cursor.getString(cursor.getColumnIndex(AppDbSchema.EvaluationTable.Cols.NAME));
                int maxPoint = cursor.getInt(cursor.getColumnIndex(AppDbSchema.EvaluationTable.Cols.MAX_POINT));
                int score = cursor.getInt(cursor.getColumnIndex(AppDbSchema.EvaluationTable.Cols.SCORE));
                String courseId = cursor.getString(cursor.getColumnIndex(AppDbSchema.EvaluationTable.Cols.COURSE_ID));
                boolean isSubEvaluation = cursor.getInt(cursor.getColumnIndex(AppDbSchema.EvaluationTable.Cols.IS_SUB_EVALUATION)) == 1;

                Evaluation evaluation = new Evaluation(UUID.fromString(uuidString));
                evaluation.setName(name);
                evaluation.setMaxPoint(maxPoint);
                evaluation.setScore(score);
                evaluation.setCourseId(courseId);
                evaluation.setSubEvaluation(isSubEvaluation);

                evaluations.add(evaluation);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return evaluations; // Retourne la liste, même si elle est vide
    }

}