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
import be.helha.b3.b3q1_android_project.models.ClassModel;

/**
 * ClassesRepository is responsible for managing the database operations related to ClassModel.
 */
public class ClassRepository {
    private final SQLiteDatabase database;

    /**
     * Constructor for the ClassesRepository.
     * @param context The application context.
     */
    public ClassRepository(Context context) {
        AppDatabaseHelper dbHelper = new AppDatabaseHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    /**
     * Get all classes from the database.
     * @return A list of ClassModel objects.
     */
    public List<ClassModel> getAll() {
        List<ClassModel> classes = new ArrayList<>();
        Cursor cursor = database.query(AppDbSchema.ClassTable.NAME, null, null, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String uuid = cursor.getString(cursor.getColumnIndexOrThrow(AppDbSchema.ClassTable.Cols.UUID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(AppDbSchema.ClassTable.Cols.NAME));
                    classes.add(new ClassModel(UUID.fromString(uuid), name));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return classes;
    }

    /**
     * Adds a new ClassModel entry to the database.
     * @param name The name of the class to be added.
     */
    public void addClass(String name) {
        ContentValues values = new ContentValues();
        values.put(AppDbSchema.ClassTable.Cols.UUID, UUID.randomUUID().toString());
        values.put(AppDbSchema.ClassTable.Cols.NAME, name);
        database.insert(AppDbSchema.ClassTable.NAME, null, values);
    }

    /**
     * Updates the name of a class in the database.
     * @param id The UUID of the class to be updated.
     * @param newName The new name of the class.
     */
    public void updateClassName(UUID id, String newName) {
        ContentValues values = new ContentValues();
        values.put(AppDbSchema.ClassTable.Cols.NAME, newName);
        database.update(AppDbSchema.ClassTable.NAME, values, AppDbSchema.ClassTable.Cols.UUID + " = ?", new String[]{id.toString()});
    }

    /**
     * Deletes a class from the database.
     * @param id The UUID of the class to be deleted.
     */
    public void deleteClass(UUID id) {
        database.delete(AppDbSchema.ClassTable.NAME, AppDbSchema.ClassTable.Cols.UUID + " = ?", new String[]{id.toString()});
    }
}
