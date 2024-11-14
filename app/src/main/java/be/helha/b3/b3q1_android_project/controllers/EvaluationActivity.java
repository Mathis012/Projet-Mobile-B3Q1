package be.helha.b3.b3q1_android_project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.helha.b3.b3q1_android_project.R;

public class EvaluationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        String courseName = getIntent().getStringExtra("COURSE_NAME");
        TextView headerText = findViewById(R.id.headerText);
        headerText.setText(courseName);

        ImageButton arrowButton = findViewById(R.id.arrowButton);
        arrowButton.setOnClickListener(v -> {
            Intent intent = new Intent(EvaluationActivity.this, EditionEvaluationActivity.class);
            startActivity(intent);
        });
    }
}