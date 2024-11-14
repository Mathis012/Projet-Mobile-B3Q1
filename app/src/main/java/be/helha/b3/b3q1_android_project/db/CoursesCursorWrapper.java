package be.helha.b3.b3q1_android_project.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import be.helha.b3.b3q1_android_project.models.Course;

public class CoursesCursorWrapper extends CursorWrapper {
    public CoursesCursorWrapper(Cursor cursor) { super(cursor); }

    public Course getCourse() {
        String uuidString =
                getString(getColumnIndex(AppDbSchema.CourseTable.Cols.UUID));
        String title =
                getString(getColumnIndex(AppDbSchema.CourseTable.Cols.NAME));
        String classId =
                getString(getColumnIndex(AppDbSchema.CourseTable.Cols.CLASS_ID));

        return new Course(UUID.fromString(uuidString), title, UUID.fromString(classId));
    }
}
