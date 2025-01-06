package be.helha.b3.b3q1_android_project.controllers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.controllers.fragments.EditionEvaluationFragment;

/**
 * Activity for managing the edition of evaluations.
 */
public class EditionEvaluationActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition_evaluation);

        FragmentManager fm = getSupportFragmentManager();
        EditionEvaluationFragment fragment = (EditionEvaluationFragment) fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new EditionEvaluationFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}