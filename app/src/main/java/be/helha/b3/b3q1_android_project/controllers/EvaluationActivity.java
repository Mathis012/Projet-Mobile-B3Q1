package be.helha.b3.b3q1_android_project.controllers;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.models.Student;

public class EvaluationActivity extends AppCompatActivity {

    private AppDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        dbHelper = new AppDatabaseHelper(this);

        String classId = getIntent().getStringExtra("CLASS_ID");
        String courseId = getIntent().getStringExtra("COURSE_ID");
        String courseName = getIntent().getStringExtra("COURSE_NAME");

        Log.d("EvaluationActivity", "CLASS_ID: " + classId);
        Log.d("EvaluationActivity", "COURSE_ID: " + courseId);

        TextView headerText = findViewById(R.id.headerText);
        headerText.setText(courseName);

        ImageButton arrowButton = findViewById(R.id.arrowButton);
        arrowButton.setOnClickListener(v -> {
            Intent intent = new Intent(EvaluationActivity.this, EditionEvaluationActivity.class);
            intent.putExtra("CLASS_ID", classId);
            intent.putExtra("COURSE_ID", courseId);
            startActivity(intent);
        });

        List<Student> students = getStudentsFromDatabase(classId);
        displayStudents(students);
    }

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

    private void displayStudents(List<Student> students) {
        LinearLayout studentListLayout = findViewById(R.id.studentListEvaluation);
        studentListLayout.removeAllViews();

        if (students.isEmpty()) {
            Log.d("EvaluationActivity", "No students to display.");
            TextView emptyTextView = new TextView(this);
            emptyTextView.setText("No students available.");
            emptyTextView.setTextSize(18);
            emptyTextView.setPadding(16, 16, 16, 16);
            studentListLayout.addView(emptyTextView);
        } else {
            for (Student student : students) {
                LinearLayout studentRow = new LinearLayout(this);
                studentRow.setOrientation(LinearLayout.HORIZONTAL);
                studentRow.setPadding(16, 16, 16, 16);

                TextView studentTextView = new TextView(this);
                studentTextView.setText(student.getFirstName());
                studentTextView.setTextSize(18);
                studentTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

                ImageButton arrowButton = new ImageButton(this);
                arrowButton.setImageResource(R.drawable.arrow_forward);
                arrowButton.setContentDescription("Voir plus");
                arrowButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                studentRow.addView(studentTextView);
                studentRow.addView(arrowButton);

                studentListLayout.addView(studentRow);
            }
        }
    }
}
