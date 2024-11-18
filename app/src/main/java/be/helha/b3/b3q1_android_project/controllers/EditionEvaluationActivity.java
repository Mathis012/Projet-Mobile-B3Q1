package be.helha.b3.b3q1_android_project.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.UUID;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.models.Evaluation;
import be.helha.b3.b3q1_android_project.models.EvaluationLab;

import android.util.Log;

public class EditionEvaluationActivity extends AppCompatActivity {

    private LinearLayout evalList;
    private AppDatabaseHelper dbHelper;
    private String currentCourseId;
    private String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edition_evaluation);

        dbHelper = new AppDatabaseHelper(this);
        evalList = findViewById(R.id.evalList);

        currentCourseId = getIntent().getStringExtra("COURSE_ID");
        courseName = getIntent().getStringExtra("COURSE_NAME");

        if (currentCourseId == null || currentCourseId.isEmpty()) {
            throw new IllegalArgumentException("COURSE_ID must be provided in the Intent");
        }

        Log.d("EditionEvaluationActivity", "COURSE_ID: " + currentCourseId);
        Log.d("EditionEvaluationActivity", "COURSE_NAME: " + courseName);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

        loadEvaluations();
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choisir le type");

        String[] options = {"Évaluation", "Sous-évaluation"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showNameAndScoreInputDialog(which == 1);
            }
        });

        builder.show();
    }

    private void showNameAndScoreInputDialog(boolean isSubEvaluation) {
        Log.d("EditionEvaluationActivity", "isSubEvaluation: " + isSubEvaluation);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_evaluation, null);

        EditText nameInput = dialogView.findViewById(R.id.evaluationName);
        EditText maxPointsInput = dialogView.findViewById(R.id.evaluationMaxPoints);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Entrer les détails");
        builder.setView(dialogView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameInput.getText().toString();
                String maxPoints = maxPointsInput.getText().toString();
                String uuid = UUID.randomUUID().toString();


                addEvaluation(isSubEvaluation, name, maxPoints, currentCourseId, uuid);
            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addEvaluation(boolean isSubEvaluation, String name, String maxPoints, String courseId, String uuid) {
        Evaluation evaluation = new Evaluation(UUID.fromString(uuid));
        evaluation.setName(name);
        evaluation.setScore(Integer.parseInt(maxPoints));
        evaluation.setMaxPoint(Integer.parseInt(maxPoints));
        evaluation.setCourseId(courseId);
        evaluation.setSubEvaluation(isSubEvaluation);

        EvaluationLab.get(this).addEvaluation(evaluation);

        loadEvaluations();
    }

    private void loadEvaluations() {
        evalList.removeAllViews();

        List<Evaluation> evaluations = EvaluationLab.get(this).getEvaluationsForCourse(currentCourseId);
        for (Evaluation evaluation : evaluations) {
            LinearLayout evaluationLayout = new LinearLayout(this);
            evaluationLayout.setOrientation(LinearLayout.HORIZONTAL);

            if (evaluation.isSubEvaluation()) {
                evaluationLayout.setPadding(50, 8, 8, 8);
            } else {
                evaluationLayout.setPadding(0, 8, 8, 8);
            }

            TextView evaluationName = new TextView(this);
            evaluationName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            evaluationName.setText(evaluation.getName());
            evaluationLayout.addView(evaluationName);

            TextView scoreView = new TextView(this);
            scoreView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            scoreView.setText(String.valueOf(evaluation.getScore()));
            evaluationLayout.addView(scoreView);

            evalList.addView(evaluationLayout);
        }
    }
}