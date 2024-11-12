package be.helha.b3.b3q1_android_project.controllers;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.dbEvaluations.EvaluationsBasesHelper;

public class EvaluationActivity extends AppCompatActivity {

    private EvaluationsBasesHelper dbHelper;
    private LinearLayout courseListLayout;
    private static final String TAG = "EvaluationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        String courseName = getIntent().getStringExtra("COURSE_NAME");
        TextView headerText = findViewById(R.id.headerText);
        headerText.setText(courseName);
    }
}