package be.helha.b3.b3q1_android_project.db;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import java.util.UUID;

import be.helha.b3.b3q1_android_project.models.Student;

public class StudentsCursorWrapper extends CursorWrapper {
    public StudentsCursorWrapper(Cursor cursor) { super (cursor); }

    public Student getStudent() {
        try {
            String uuidString = getString(getColumnIndex(AppDbSchema.StudentTable.Cols.UUID));
            String firstname = getString(getColumnIndex(AppDbSchema.StudentTable.Cols.NAME));
            String classe = getString(getColumnIndex(AppDbSchema.StudentTable.Cols.CLASS_ID));

            return new Student(UUID.fromString(uuidString), firstname, classe);
        } catch (Exception e) {
            Log.e("StudentsCursorWrapper", "Error retrieving student from cursor", e);
            return null;
        }
    }
}
