package be.helha.b3.b3q1_android_project.controllers;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.controllers.fragments.ClassListFragment;

/**
 * MainActivity is the main entry point of the application.
 *  * It hosts the ClassListFragment to display the list of classes.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ClassListFragment())
                    .commit();
        }
    }
}