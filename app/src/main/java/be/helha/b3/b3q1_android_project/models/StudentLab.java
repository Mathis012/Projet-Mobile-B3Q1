package be.helha.b3.b3q1_android_project.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.db.StudentsCursorWrapper;

public class StudentLab {
    private static StudentLab sStudentLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static StudentLab get(Context context) {
        if (sStudentLab == null) {
            sStudentLab = new StudentLab(context);
        }
        return sStudentLab;
    }

    private StudentLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new AppDatabaseHelper(mContext).getWritableDatabase();
    }

    public void addStudent(Student student) {
        mDatabase.insert(AppDbSchema.StudentTable.NAME, null, getContentValues(student));
    }

    public Student getStudent(String uuid) {
        StudentsCursorWrapper cursor = queryStudents(AppDbSchema.StudentTable.Cols.UUID + "=?", new String[]{uuid});
        try {
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getStudent();
        } finally {
            cursor.close();
        }
    }

    public List<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();
        StudentsCursorWrapper cursor = queryStudents(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                students.add(cursor.getStudent());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return students;
    }

    private ContentValues getContentValues(Student student) {
        ContentValues values = new ContentValues();
        values.put(AppDbSchema.StudentTable.Cols.UUID, student.getId().toString());
        values.put(AppDbSchema.StudentTable.Cols.NAME, student.getFirstName());
        values.put(AppDbSchema.StudentTable.Cols.CLASS_ID, student.getClasse());
        return values;
    }

    private StudentsCursorWrapper queryStudents(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                AppDbSchema.StudentTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, null, null
        );
        return new StudentsCursorWrapper(cursor);
    }
}
