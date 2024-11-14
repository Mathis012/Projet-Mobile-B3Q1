package be.helha.b3.b3q1_android_project.controllers;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.db.StudentsCursorWrapper;
import be.helha.b3.b3q1_android_project.models.Student;

public class StudentActivity extends AppCompatActivity {

    private AppDatabaseHelper dbHelper;
    private LinearLayout studentListLayout;
    private ImageButton addButton;
    private TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        dbHelper = new AppDatabaseHelper(this);
        studentListLayout = findViewById(R.id.classListStudent);
        addButton = findViewById(R.id.addButton);
        headerText = findViewById(R.id.headerText);

        Intent intent = getIntent();
        String className = intent.getStringExtra("CLASS_NAME");
        headerText.setText("Les étudiants de " + className);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, AddStudentActivity.class);
                intent.putExtra("CLASS_NAME", className);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String className = intent.getStringExtra("CLASS_NAME");
        List<Student> students = getStudentsFromDatabase(className);
        displayStudents(students);
    }

    private List<Student> getStudentsFromDatabase(String className) {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                AppDbSchema.StudentTable.NAME,
                null,
                AppDbSchema.StudentTable.Cols.CLASS_ID + " = ?",
                new String[]{className},
                null, null, null
        );

        try {
            StudentsCursorWrapper cursorWrapper = new StudentsCursorWrapper(cursor);
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                students.add(cursorWrapper.getStudent());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return students;
    }

    private void displayStudents(List<Student> students) {
        studentListLayout.removeAllViews();
        if (students.isEmpty()) {
            TextView emptyTextView = new TextView(this);
            emptyTextView.setText("No students found.");
            emptyTextView.setTextSize(18);
            emptyTextView.setPadding(12, 12, 12, 12);
            studentListLayout.addView(emptyTextView);
        } else {
            for (Student student : students) {
                LinearLayout studentLayout = new LinearLayout(this);
                studentLayout.setOrientation(LinearLayout.HORIZONTAL);
                studentLayout.setPadding(12, 12, 12, 12);

                TextView studentNameTextView = new TextView(this);
                studentNameTextView.setText(student.getFirstName());
                studentNameTextView.setTextSize(18);
                studentNameTextView.setPadding(12, 0, 12, 0);

                studentLayout.addView(studentNameTextView);
                studentListLayout.addView(studentLayout);
            }
        }
    }
}
