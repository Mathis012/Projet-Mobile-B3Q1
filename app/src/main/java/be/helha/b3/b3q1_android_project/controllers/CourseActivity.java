package be.helha.b3.b3q1_android_project.controllers;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.controllers.fragments.CourseListFragment;

/**
 * CourseActivity is responsible for displaying the list of courses.
 * It hosts the CourseListFragment to display the list of courses.
 */
public class CourseActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        if (savedInstanceState == null) {
            CourseListFragment fragment = new CourseListFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}