package be.helha.b3.b3q1_android_project.controllers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.controllers.fragments.StudentGradesFragment;

/**
 * Activity for displaying and managing grades for a specific student in a specific course.
 */
public class StudentGradesActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied.
     */
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