package be.helha.b3.b3q1_android_project.controllers;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.dbClasses.ClassesDbSchema;
import be.helha.b3.b3q1_android_project.dbCourses.CoursesBasesHelper;
import be.helha.b3.b3q1_android_project.dbCourses.CoursesDbSchema;
import be.helha.b3.b3q1_android_project.models.Course;

public class CourseActivity extends AppCompatActivity {
    private LinearLayout courseList;
    private CoursesBasesHelper dbHelper;
    private static final String TAG = "CourseActivity";
    private String classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course);

        classId = getIntent().getStringExtra("CLASS_ID");
        String className = getIntent().getStringExtra("CLASS_NAME");
        TextView headerText = findViewById(R.id.headerText);
        headerText.setText("Cours de " + className);

        ImageButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            try{
                addNewCourse("New Course");
                loadCourses();
            } catch (Exception e) {
                Log.e(TAG, "Error adding new course", e);
            }
        });

        dbHelper = new CoursesBasesHelper(this);
        courseList = findViewById(R.id.courseList);

        loadCourses();
    }

    private void loadCourses() {
        courseList.removeAllViews();
        List<Course> aCourses = getCoursesFromDatabase();
        for (Course aCourse : aCourses) {
            View courseView = getLayoutInflater().inflate(R.layout.course_item, courseList, false);
            TextView courseName = courseView.findViewById(R.id.courseName);
            EditText courseNameEdit = courseView.findViewById(R.id.courseNameEdit);
            courseName.setText(aCourse.getName());

            ImageButton checkButton = courseView.findViewById(R.id.checkButton);
            checkButton.setOnClickListener(v -> {
                courseName.setText(courseNameEdit.getText());
                courseName.setVisibility(View.VISIBLE);
                courseNameEdit.setVisibility(View.GONE);
                checkButton.setVisibility(View.GONE);

                updateCourseName(aCourse.getId(), courseName.getText().toString());
            });

            ImageButton modifButton = courseView.findViewById(R.id.modifButton);
            modifButton.setOnClickListener(v -> {
                courseName.setVisibility(View.GONE);
                courseNameEdit.setVisibility(View.VISIBLE);
                courseNameEdit.setText(courseName.getText());
                checkButton.setVisibility(View.VISIBLE);
            });

            courseName.setOnClickListener(v -> {
                Intent intent = new Intent(CourseActivity.this, EvaluationActivity.class);
                intent.putExtra("COURSE_NAME", aCourse.getName());
                startActivity(intent);
            });

            courseList.addView(courseView);
        }
    }

    private List<Course> getCoursesFromDatabase() {
        List<Course> aCourses = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                CoursesDbSchema.CoursesTable.NAME,
                null,
                CoursesDbSchema.CoursesTable.cols.CLASS_ID + " = ?",
                new String[]{classId},
                null, null, null
        );
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String uuid = cursor.getString(cursor.getColumnIndexOrThrow(CoursesDbSchema.CoursesTable.cols.UUID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CoursesDbSchema.CoursesTable.cols.NAME));
                aCourses.add(new Course(UUID.fromString(uuid), name, UUID.randomUUID()));// A VERIF !!!
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return aCourses;
    }

    private void addNewCourse(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CoursesDbSchema.CoursesTable.cols.UUID, UUID.randomUUID().toString());
        values.put(CoursesDbSchema.CoursesTable.cols.NAME, name);
        values.put(CoursesDbSchema.CoursesTable.cols.CLASS_ID, classId);
        db.insert(CoursesDbSchema.CoursesTable.NAME, null, values);
    }

    private void updateCourseName(UUID courseId, String newName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CoursesDbSchema.CoursesTable.cols.NAME, newName);
        db.update(CoursesDbSchema.CoursesTable.NAME, values, CoursesDbSchema.CoursesTable.cols.UUID + " = ?", new String[]{courseId.toString()});
    }
}