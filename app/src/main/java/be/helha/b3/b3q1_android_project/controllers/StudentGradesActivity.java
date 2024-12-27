package be.helha.b3.b3q1_android_project.controllers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.controllers.fragments.StudentGradesFragment;

public class StudentGradesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_grades);

        String studentId = getIntent().getStringExtra("STUDENT_ID");
        String courseId = getIntent().getStringExtra("COURSE_ID");

        if (savedInstanceState == null) {
            StudentGradesFragment fragment = StudentGradesFragment.newInstance(studentId, courseId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}