package be.helha.b3.b3q1_android_project.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.models.Course;

/**
 * CourseRepository manages database operations for Course objects.
 */
public class CourseRepository {
    private final SQLiteDatabase database;

    /**
     * Constructor for CourseRepository.
     * @param context The application context.
     */
    public CourseRepository(Context context) {
        AppDatabaseHelper dbHelper = new AppDatabaseHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    /**
     * Retrieves all courses from the database.
     * @param classId the ID of the class
     * @return A list of courses
     */
    public List<Course> getCoursesForClass(String classId) {
        List<Course> courses = new ArrayList<>();
        Cursor cursor = database.query(
                AppDbSchema.CourseTable.NAME,
                null,
                AppDbSchema.CourseTable.Cols.CLASS_ID + " = ?",
                new String[]{classId},
                null, null, null
        );
        try {
            if (cursor.moveToFirst()) {
                do {
                    String uuid = cursor.getString(cursor.getColumnIndexOrThrow(AppDbSchema.CourseTable.Cols.UUID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(AppDbSchema.CourseTable.Cols.NAME));
                    String classIdFromDb = cursor.getString(cursor.getColumnIndexOrThrow(AppDbSchema.CourseTable.Cols.CLASS_ID));
                    courses.add(new Course(UUID.fromString(uuid), name, UUID.fromString(classIdFromDb)));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return courses;
    }

    /**
     * Adds a new course to the database
     * @param name The name of the course
     * @param classId The ID of the associated class
     */
    public void addCourse(String name, String classId) {
        ContentValues values = new ContentValues();
        values.put(AppDbSchema.CourseTable.Cols.UUID, UUID.randomUUID().toString());
        values.put(AppDbSchema.CourseTable.Cols.NAME, name);
        values.put(AppDbSchema.CourseTable.Cols.CLASS_ID, classId);
        database.insert(AppDbSchema.CourseTable.NAME, null, values);
    }

    /**
     * Updates the name of a course in the database.
     * @param id The UUID of the course to update
     * @param newName The new name of the course
     */
    public void updateCourseName(UUID id, String newName) {
        ContentValues values = new ContentValues();
        values.put(AppDbSchema.CourseTable.Cols.NAME, newName);
        database.update(AppDbSchema.CourseTable.NAME, values, AppDbSchema.CourseTable.Cols.UUID + " = ?", new String[]{id.toString()});
    }
}