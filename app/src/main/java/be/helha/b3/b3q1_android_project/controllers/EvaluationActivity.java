package be.helha.b3.b3q1_android_project.controllers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.controllers.fragments.EvaluationFragment;

public class EvaluationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        FragmentManager fm = getSupportFragmentManager();
        EvaluationFragment fragment = (EvaluationFragment) fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new EvaluationFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}