package be.helha.b3.b3q1_android_project.controllers;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.models.Class;

public class MainActivity extends AppCompatActivity {

    private AppDatabaseHelper dbHelper;
    private LinearLayout classListLayout;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            try{
                addNewClass("New Class");
                loadClasses();
            } catch (Exception e) {
                Log.e(TAG, "Error adding new class", e);
            }
        });

        dbHelper = new AppDatabaseHelper(this);
        classListLayout = findViewById(R.id.classList);

        loadClasses();
    }

    private void loadClasses() {
        classListLayout.removeAllViews();
        List<Class> aClasses = getClassesFromDatabase();
        for (Class aClass : aClasses) {
            View classView = getLayoutInflater().inflate(R.layout.class_item, classListLayout, false);
            TextView className = classView.findViewById(R.id.className);
            EditText classNameEdit = classView.findViewById(R.id.classNameEdit);
            className.setText(aClass.getName());

            ImageButton courseButton = classView.findViewById(R.id.courseButton);
            courseButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, CourseActivity.class);
                intent.putExtra("CLASS_ID", aClass.getId().toString());
                intent.putExtra("CLASS_NAME", aClass.getName());
                startActivity(intent);
            });

            ImageButton studentButton = classView.findViewById(R.id.studentButton);
            studentButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                String classNameText = className.getText().toString();
                intent.putExtra("CLASS_NAME", classNameText);
                intent.putExtra("CLASS_ID", aClass.getId().toString());
                startActivity(intent);

                Log.d("MainActivity", "CLASS_ID sent to StudentActivity: " + aClass.getId().toString());
            });

            ImageButton trashButton = classView.findViewById(R.id.trashButton);
            trashButton.setOnClickListener(v -> {
                deleteClass(aClass.getId());
                loadClasses();
            });

            ImageButton checkButton = classView.findViewById(R.id.checkButton);
            checkButton.setOnClickListener(v -> {
                className.setText(classNameEdit.getText());
                className.setVisibility(View.VISIBLE);
                classNameEdit.setVisibility(View.GONE);
                courseButton.setVisibility(View.VISIBLE);
                studentButton.setVisibility(View.VISIBLE);
                trashButton.setVisibility(View.GONE);
                checkButton.setVisibility(View.GONE);

                updateClassName(aClass.getId(), className.getText().toString());
            });

            ImageButton modifButton = classView.findViewById(R.id.modifButton);
            modifButton.setOnClickListener(v -> {
                className.setVisibility(View.GONE);
                classNameEdit.setVisibility(View.VISIBLE);
                classNameEdit.setText(className.getText());
                courseButton.setVisibility(View.GONE);
                studentButton.setVisibility(View.GONE);
                trashButton.setVisibility(View.VISIBLE);
                checkButton.setVisibility(View.VISIBLE);
            });

            classListLayout.addView(classView);
        }
    }

    private List<Class> getClassesFromDatabase() {
        List<Class> aClasses = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                AppDbSchema.ClassTable.NAME,
                null, null, null, null, null, null
        );
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String uuid = cursor.getString(cursor.getColumnIndexOrThrow(AppDbSchema.ClassTable.Cols.UUID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(AppDbSchema.ClassTable.Cols.NAME));
                aClasses.add(new Class(UUID.fromString(uuid), name));
                Log.d("MainActivity", "CLASS_ID retrieved from database: " + uuid);
                cursor.moveToNext();
                }
        } finally {
            cursor.close();
        }
        return aClasses;
    }

    private void addNewClass(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppDbSchema.ClassTable.Cols.UUID, UUID.randomUUID().toString());
        values.put(AppDbSchema.ClassTable.Cols.NAME, name);
        db.insert(AppDbSchema.ClassTable.NAME, null, values);
    }

    private void updateClassName(UUID classId, String Newname) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppDbSchema.ClassTable.Cols.NAME, Newname);
        db.update(AppDbSchema.ClassTable.NAME, values, AppDbSchema.ClassTable.Cols.UUID + " = ?", new String[]{classId.toString()});
    }

    private void deleteClass(UUID id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(AppDbSchema.ClassTable.NAME, AppDbSchema.ClassTable.Cols.UUID + " = ?", new String[]{id.toString()});
    }
}