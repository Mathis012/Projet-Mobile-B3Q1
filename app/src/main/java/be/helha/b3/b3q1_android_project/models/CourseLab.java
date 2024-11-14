package be.helha.b3.b3q1_android_project.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.db.CoursesCursorWrapper;

public class CourseLab {
    private static CourseLab sCourseLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CourseLab get(Context context) {
        if (sCourseLab == null) {
            sCourseLab = new CourseLab(context);
        }
        return sCourseLab;
    }

    private CourseLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new AppDatabaseHelper(mContext).getWritableDatabase();
    }

    public void addCourse(Course course) {
        mDatabase.insert(AppDbSchema.CourseTable.NAME, null, getContentValues(course));
    }

    public Course getCourse(String uuid) {
        CoursesCursorWrapper cursor = queryCourses(AppDbSchema.CourseTable.Cols.UUID + "=?", new String[]{uuid});
        try {
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getCourse();
        } finally {
            cursor.close();
        }
    }

    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        CoursesCursorWrapper cursor = queryCourses(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                courses.add(cursor.getCourse());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return courses;
    }

    private ContentValues getContentValues(Course course) {
        ContentValues values = new ContentValues();
        values.put(AppDbSchema.CourseTable.Cols.UUID, course.getId().toString());
        values.put(AppDbSchema.CourseTable.Cols.NAME, course.getName());
        values.put(AppDbSchema.CourseTable.Cols.CLASS_ID, course.getClasseId().toString());
        return values;
    }

    private CoursesCursorWrapper queryCourses(String whereClause, String[] whereArgs) {
        return new CoursesCursorWrapper(mDatabase.query(
                AppDbSchema.CourseTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, null, null));
    }
}