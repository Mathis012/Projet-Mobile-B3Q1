package be.helha.b3.b3q1_android_project.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.models.Student;

/**
 * Repository class for managing student data.
 */
public class StudentRepository {
    private final SQLiteDatabase database;

    /**
     * Constructor for StudentRepository.
     * @param context The context used to open or create the database.
     */
    public StudentRepository(Context context) {
        AppDatabaseHelper dbHelper = new AppDatabaseHelper(context);
        this.database = dbHelper.getWritableDatabase();;
    }

    /**
     * Retrieves a list of students for a specific class.
     * @param classId The ID of the class.
     * @return A list of students in the specified class.
     */
    public List<Student> getStudentsForClass(String classId) {
        List<Student> students = new ArrayList<>();
        Cursor cursor = database.query(
                AppDbSchema.StudentTable.NAME,
                null,
                AppDbSchema.StudentTable.Cols.CLASS_ID + " = ?",
                new String[]{classId},
                null, null, null
        );
        try {
            if (cursor.moveToFirst()) {
                do {
                    String uuid = cursor.getString(cursor.getColumnIndexOrThrow(AppDbSchema.StudentTable.Cols.UUID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(AppDbSchema.StudentTable.Cols.NAME));
                    students.add(new Student(UUID.fromString(uuid), name, classId));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return students;
    }
}
