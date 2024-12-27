package be.helha.b3.b3q1_android_project.controllers.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.adapters.EditionEvaluationAdapter;
import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.models.Evaluation;
import be.helha.b3.b3q1_android_project.models.EvaluationLab;

public class EditionEvaluationFragment extends Fragment {

    private RecyclerView evalList;
    private AppDatabaseHelper dbHelper;
    private String currentCourseId;
    private String courseName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edition_evaluation, container, false);

        dbHelper = new AppDatabaseHelper(getContext());
        evalList = view.findViewById(R.id.evalList);
        evalList.setLayoutManager(new LinearLayoutManager(getContext()));

        currentCourseId = getActivity().getIntent().getStringExtra("COURSE_ID");
        courseName = getActivity().getIntent().getStringExtra("COURSE_NAME");

        if (currentCourseId == null || currentCourseId.isEmpty()) {
            throw new IllegalArgumentException("COURSE_ID must be provided in the Intent");
        }

        view.findViewById(R.id.addButton).setOnClickListener(v -> showAddDialog());

        loadEvaluations(evalList);

        return view;
    }

    private void loadEvaluations(RecyclerView evalList) {
        List<Evaluation> evaluations = EvaluationLab.get(getContext()).getEvaluationsForCourse(currentCourseId);
        EditionEvaluationAdapter adapter = new EditionEvaluationAdapter(getContext(), evaluations);
        evalList.setAdapter(adapter);
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choisir le type");

        String[] options = {"Évaluation", "Sous-évaluation"};
        builder.setItems(options, (dialog, which) -> showNameAndScoreInputDialog(which == 1));

        builder.show();
    }

    private void showNameAndScoreInputDialog(boolean isSubEvaluation) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_evaluation, null);

        EditText nameInput = dialogView.findViewById(R.id.evaluationName);
        EditText maxPointsInput = dialogView.findViewById(R.id.evaluationMaxPoints);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Entrer les détails");
        builder.setView(dialogView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String name = nameInput.getText().toString();
            String maxPoints = maxPointsInput.getText().toString();
            String uuid = UUID.randomUUID().toString();

            addEvaluation(isSubEvaluation, name, maxPoints, currentCourseId, uuid, evalList);
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void addEvaluation(boolean isSubEvaluation, String name, String maxPoints, String courseId, String uuid, RecyclerView evalList) {
        Evaluation evaluation = new Evaluation(UUID.fromString(uuid));
        evaluation.setName(name);
        evaluation.setScore(Integer.parseInt(maxPoints));
        evaluation.setMaxPoint(Integer.parseInt(maxPoints));
        evaluation.setCourseId(courseId);
        evaluation.setSubEvaluation(isSubEvaluation);

        EvaluationLab.get(getContext()).addEvaluation(evaluation);

        loadEvaluations(evalList);
    }
}