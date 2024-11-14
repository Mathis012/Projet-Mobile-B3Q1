package be.helha.b3.b3q1_android_project.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import be.helha.b3.b3q1_android_project.R;

public class EditionEvaluationActivity extends AppCompatActivity {

    private LinearLayout evalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edition_evaluation);

        evalList = findViewById(R.id.evalList);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choisir le type");

        String[] options = {"Évaluation", "Sous-évaluation"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showNameAndScoreInputDialog(which == 1);
            }
        });

        builder.show();
    }

    private void showNameAndScoreInputDialog(boolean isSubEvaluation) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_evaluation, null);

        EditText nameInput = dialogView.findViewById(R.id.evaluationName);
        EditText scoreInput = dialogView.findViewById(R.id.evaluationScore);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Entrer les détails");
        builder.setView(dialogView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameInput.getText().toString();
                String score = scoreInput.getText().toString();
                addEvaluation(isSubEvaluation, name, score);
            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addEvaluation(boolean isSubEvaluation, String name, String score) {
        LinearLayout evaluationLayout = new LinearLayout(this);
        evaluationLayout.setOrientation(LinearLayout.HORIZONTAL);
        evaluationLayout.setPadding(isSubEvaluation ? 32 : 0, 8, 8, 8);

        TextView evaluationName = new TextView(this);
        evaluationName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        evaluationName.setText(name);
        evaluationLayout.addView(evaluationName);

        TextView scoreView = new TextView(this);
        scoreView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        scoreView.setText(score);
        evaluationLayout.addView(scoreView);

        evalList.addView(evaluationLayout);
    }
}