package be.helha.b3.b3q1_android_project.dbCourses;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import be.helha.b3.b3q1_android_project.models.Course;

public class CoursesCursorWrapper extends CursorWrapper {
    public CoursesCursorWrapper(Cursor cursor) { super(cursor); }

    public Course getCourse() {
        String uuidString=
                getString(getColumnIndex(CoursesDbSchema.CoursesTable.cols.UUID));
        String name=
                getString(getColumnIndex(CoursesDbSchema.CoursesTable.cols.NAME));
        String classeString=
                getString(getColumnIndex(CoursesDbSchema.CoursesTable.cols.CLASS_ID));
        UUID classe = UUID.fromString(classeString);
        return new Course (UUID.fromString(uuidString), name, classe);
    }
}
