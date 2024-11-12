package be.helha.b3.b3q1_android_project.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import be.helha.b3.b3q1_android_project.dbCourses.CoursesBasesHelper;
import be.helha.b3.b3q1_android_project.dbCourses.CoursesCursorWrapper;
import be.helha.b3.b3q1_android_project.dbCourses.CoursesDbSchema;

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
        mDatabase = new CoursesBasesHelper(mContext).getWritableDatabase();
    }

    public void addCourse(Course course) {
        mDatabase.insert(CoursesDbSchema.CoursesTable.NAME, null, getContentValues(course));
    }

    public Course getCourse(String uuid) {
        CoursesCursorWrapper cursor = queryCourses(CoursesDbSchema.CoursesTable.cols.UUID + "=?", new String[]{uuid});
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
        values.put(CoursesDbSchema.CoursesTable.cols.UUID, course.getId().toString());
        values.put(CoursesDbSchema.CoursesTable.cols.NAME, course.getName());
        values.put(CoursesDbSchema.CoursesTable.cols.CLASS_ID, course.getClasseId().toString());
        return values;
    }

    private CoursesCursorWrapper queryCourses(String whereClause, String[] whereArgs) {
        return new CoursesCursorWrapper(mDatabase.query(
                CoursesDbSchema.CoursesTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, null, null));
    }
}