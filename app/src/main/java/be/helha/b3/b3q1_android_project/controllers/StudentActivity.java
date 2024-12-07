package be.helha.b3.b3q1_android_project.controllers;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.controllers.fragments.StudentListFragment;

/**
 * Activity for displaying a list of students in a class.
 */
public class StudentActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        String classId = getIntent().getStringExtra("CLASS_ID");
        String className = getIntent().getStringExtra("CLASS_NAME");

        if (savedInstanceState == null) {
            StudentListFragment fragment = new StudentListFragment();
            Bundle args = new Bundle();
            args.putString("CLASS_ID", classId);
            args.putString("CLASS_NAME", className);
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
