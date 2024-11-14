package be.helha.b3.b3q1_android_project.controllers;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;

public class AddStudentActivity extends AppCompatActivity {

    private AppDatabaseHelper dbHelper;
    private LinearLayout studentListLayout;
    private List<EditText> studentNameEdits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        dbHelper = new AppDatabaseHelper(this);
        studentListLayout = findViewById(R.id.studentList);
        studentNameEdits = new ArrayList<>();

        Intent intent = getIntent();
        String className = intent.getStringExtra("CLASS_NAME");

        Button addButton = findViewById(R.id.addStudentButton);
        addButton.setOnClickListener(v -> addNewStudentInput());

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveStudentsAndReturn());
    }

    private void addNewStudentInput() {
        View studentView = getLayoutInflater().inflate(R.layout.student_item, studentListLayout, false);
        EditText studentNameEdit = studentView.findViewById(R.id.studentNameEdit);
        studentNameEdits.add(studentNameEdit);
        studentListLayout.addView(studentView);
    }

    private void saveStudentsAndReturn() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (EditText studentNameEdit : studentNameEdits) {
            String studentName = studentNameEdit.getText().toString();
            if (!studentName.isEmpty()) {
                ContentValues values = new ContentValues();
                values.put(AppDbSchema.StudentTable.Cols.UUID, UUID.randomUUID().toString());
                values.put(AppDbSchema.StudentTable.Cols.NAME, studentName);
                values.put(AppDbSchema.StudentTable.Cols.CLASS_ID, getIntent().getStringExtra("CLASS_NAME"));
                db.insert(AppDbSchema.StudentTable.NAME, null, values);
            }
        }
        finish();
    }
}