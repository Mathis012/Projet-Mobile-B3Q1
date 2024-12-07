package be.helha.b3.b3q1_android_project.controllers;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.controllers.fragments.AddStudentFragment;

public class AddStudentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        String classId = getIntent().getStringExtra("CLASS_ID");

        if (savedInstanceState == null) {
            AddStudentFragment fragment = new AddStudentFragment();
            Bundle args = new Bundle();
            args.putString("CLASS_ID", classId);
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
