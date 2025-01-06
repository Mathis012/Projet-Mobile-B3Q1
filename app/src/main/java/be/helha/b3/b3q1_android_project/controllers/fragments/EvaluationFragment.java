package be.helha.b3.b3q1_android_project.controllers.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.adapters.EvaluationAdapter;
import be.helha.b3.b3q1_android_project.controllers.EditionEvaluationActivity;
import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.models.Student;

/**
 * Fragment for displaying and managing evaluations for a specific course.
 */
public class EvaluationFragment extends Fragment {

    private AppDatabaseHelper dbHelper;

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);

        dbHelper = new AppDatabaseHelper(getContext());

        String classId = getActivity().getIntent().getStringExtra("CLASS_ID");
        String courseId = getActivity().getIntent().getStringExtra("COURSE_ID");
        String courseName = getActivity().getIntent().getStringExtra("COURSE_NAME");

        Log.d("EvaluationFragment", "CLASS_ID: " + classId);
        Log.d("EvaluationFragment", "COURSE_ID: " + courseId);

        TextView headerText = view.findViewById(R.id.headerText);
        headerText.setText(courseName);

        ImageButton arrowButton = view.findViewById(R.id.arrowButton);
        arrowButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditionEvaluationActivity.class);
            intent.putExtra("CLASS_ID", classId);
            intent.putExtra("COURSE_ID", courseId);
            intent.putExtra("COURSE_NAME", courseName);
            startActivity(intent);
        });

        List<Student> students = getStudentsFromDatabase(classId);
        displayStudents(view, students, courseId, classId);

        return view;
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     */
    @Override
    public void onResume() {
        super.onResume();

        String classId = getActivity().getIntent().getStringExtra("CLASS_ID");
        String courseId = getActivity().getIntent().getStringExtra("COURSE_ID");

        List<Student> students = getStudentsFromDatabase(classId);
        displayStudents(getView(), students, courseId, classId);
    }

    /**
     * Retrieves the list of students from the database for a specific class.
     * @param classId The ID of the class.
     * @return The list of students.
     */
    private List<Student> getStudentsFromDatabase(String classId) {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                AppDbSchema.StudentTable.NAME,
                null,
                AppDbSchema.StudentTable.Cols.CLASS_ID + " = ?",
                new String[]{classId},
                null, null, null
        );

        try {
            while (cursor.moveToNext()) {
                String studentId = cursor.getString(cursor.getColumnIndexOrThrow(AppDbSchema.StudentTable.Cols.UUID));
                String studentName = cursor.getString(cursor.getColumnIndexOrThrow(AppDbSchema.StudentTable.Cols.NAME));
                String studentClassId = cursor.getString(cursor.getColumnIndexOrThrow(AppDbSchema.StudentTable.Cols.CLASS_ID));

                students.add(new Student(UUID.fromString(studentId), studentName, studentClassId));
            }
        } finally {
            cursor.close();
        }

        return students;
    }

    /**
     * Displays the list of students in a RecyclerView.
     * @param view The view containing the RecyclerView.
     * @param students The list of students to display.
     * @param courseId The ID of the course.
     * @param classId The ID of the class.
     */
    private void displayStudents(View view, List<Student> students, String courseId, String classId) {
        RecyclerView recyclerView = view.findViewById(R.id.studentListEvaluation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        EvaluationAdapter adapter = new EvaluationAdapter(getContext(), students, courseId, classId);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Calculates the average score of a student for a specific course on a scale of 20.
     * @param studentId The ID of the student.
     * @param courseId The ID of the course.
     * @return The average score on a scale of 20.
     */
    private double calculateAverageOn20(String studentId, String courseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT e." + AppDbSchema.EvaluationTable.Cols.MAX_POINT + ", g." + AppDbSchema.GradeTable.Cols.SCORE +
                        " FROM " + AppDbSchema.EvaluationTable.NAME + " e" +
                        " LEFT JOIN " + AppDbSchema.GradeTable.NAME + " g" +
                        " ON e." + AppDbSchema.EvaluationTable.Cols.UUID + " = g." + AppDbSchema.GradeTable.Cols.EVALUATION_ID +
                        " WHERE e." + AppDbSchema.EvaluationTable.Cols.COURSE_ID + " = ? AND g." + AppDbSchema.GradeTable.Cols.STUDENT_ID + " = ?",
                new String[]{courseId, studentId}
        );

        double totalScore = 0;
        double totalMaxPoints = 0;

        try {
            while (cursor.moveToNext()) {
                int maxPoint = cursor.getInt(cursor.getColumnIndexOrThrow(AppDbSchema.EvaluationTable.Cols.MAX_POINT));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(AppDbSchema.GradeTable.Cols.SCORE));

                totalScore += score;
                totalMaxPoints += maxPoint;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        if (totalMaxPoints == 0) return 0;

        return (totalScore / totalMaxPoints) * 20;
    }
}