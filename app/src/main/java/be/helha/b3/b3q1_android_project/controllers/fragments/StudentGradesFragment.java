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

/**
 * Fragment for displaying and managing grades for a specific student in a specific course.
 */
public class StudentGradesFragment extends Fragment {

    private static final String ARG_STUDENT_ID = "STUDENT_ID";
    private static final String ARG_COURSE_ID = "COURSE_ID";

    private AppDatabaseHelper dbHelper = new AppDatabaseHelper(getContext());
    private String studentId;
    private String courseId;

    /**
     * Creates a new instance of StudentGradesFragment with the provided student ID and course ID.
     * @param studentId The ID of the student.
     * @param courseId The ID of the course.
     * @return A new instance of StudentGradesFragment.
     */
    public static StudentGradesFragment newInstance(String studentId, String courseId) {
        StudentGradesFragment fragment = new StudentGradesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STUDENT_ID, studentId);
        args.putString(ARG_COURSE_ID, courseId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
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

    /**
     * Loads the evaluations for the specified student and course, and sets the adapter for the RecyclerView.
     * @param view The view containing the RecyclerView.
     * @param studentId The ID of the student.
     * @param courseId The ID of the course.
     */
    private void loadEvaluationsForStudent(View view, String studentId, String courseId) {
        RecyclerView recyclerView = view.findViewById(R.id.gradesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Evaluation> evaluations = getEvaluationsForCourse(courseId);
        EvaluationGradesAdapter adapter = new EvaluationGradesAdapter(getContext(), evaluations, studentId);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Retrieves the list of evaluations for a specific course from the database.
     * @param courseId The ID of the course.
     * @return The list of evaluations.
     */
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