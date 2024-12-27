package be.helha.b3.b3q1_android_project.controllers.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.adapters.EvaluationGradesAdapter;
import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.models.Evaluation;

public class StudentGradesFragment extends Fragment {

    private static final String ARG_STUDENT_ID = "STUDENT_ID";
    private static final String ARG_COURSE_ID = "COURSE_ID";

    private AppDatabaseHelper dbHelper = new AppDatabaseHelper(getContext());
    private String studentId;
    private String courseId;

    public static StudentGradesFragment newInstance(String studentId, String courseId) {
        StudentGradesFragment fragment = new StudentGradesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STUDENT_ID, studentId);
        args.putString(ARG_COURSE_ID, courseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_grades, container, false);

        if (getArguments() != null) {
            studentId = getArguments().getString(ARG_STUDENT_ID);
            courseId = getArguments().getString(ARG_COURSE_ID);
        }

            loadEvaluationsForStudent(view, studentId, courseId);

        return view;
    }

    private void loadEvaluationsForStudent(View view, String studentId, String courseId) {
        RecyclerView recyclerView = view.findViewById(R.id.gradesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Evaluation> evaluations = getEvaluationsForCourse(courseId);
        EvaluationGradesAdapter adapter = new EvaluationGradesAdapter(getContext(), evaluations, studentId);
        recyclerView.setAdapter(adapter);
    }

    private List<Evaluation> getEvaluationsForCourse(String courseId) {
        Context context = getContext();
        if (context == null) {
            throw new IllegalStateException("Context is null");
        }

        SQLiteDatabase db = new AppDatabaseHelper(context).getReadableDatabase();
        List<Evaluation> evaluations = new ArrayList<>();
        Cursor cursor = db.query(
                AppDbSchema.EvaluationTable.NAME,
                null,
                AppDbSchema.EvaluationTable.Cols.COURSE_ID + " = ?",
                new String[]{courseId},
                null, null, null
        );

        try {
            while (cursor.moveToNext()) {
                String uuidString = cursor.getString(cursor.getColumnIndex(AppDbSchema.EvaluationTable.Cols.UUID));
                String name = cursor.getString(cursor.getColumnIndex(AppDbSchema.EvaluationTable.Cols.NAME));
                int maxPoint = cursor.getInt(cursor.getColumnIndex(AppDbSchema.EvaluationTable.Cols.MAX_POINT));
                int score = cursor.getInt(cursor.getColumnIndex(AppDbSchema.EvaluationTable.Cols.SCORE));
                boolean isSubEvaluation = cursor.getInt(cursor.getColumnIndex(AppDbSchema.EvaluationTable.Cols.IS_SUB_EVALUATION)) == 1;

                Evaluation evaluation = new Evaluation(UUID.fromString(uuidString));
                evaluation.setName(name);
                evaluation.setMaxPoint(maxPoint);
                evaluation.setScore(score);
                evaluation.setCourseId(courseId);
                evaluation.setSubEvaluation(isSubEvaluation);

                evaluations.add(evaluation);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return evaluations;
    }
}