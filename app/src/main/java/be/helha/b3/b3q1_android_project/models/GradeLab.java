package be.helha.b3.b3q1_android_project.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.db.StudentGradesCursorWrapper;

public class GradeLab {
    private static GradeLab sGradeLab;
    private final Context mContext;
    private final SQLiteDatabase mDatabase;

    private GradeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new AppDatabaseHelper(mContext).getWritableDatabase();
    }

    public static GradeLab get(Context context) {
        if (sGradeLab == null) {
            sGradeLab = new GradeLab(context);
        }
        return sGradeLab;
    }

    public void addGrade(Grade grade) {
        ContentValues values = getContentValues(grade);
        mDatabase.insert(AppDbSchema.GradeTable.NAME, null, values);
    }

    public List<Grade> getGradesForStudent(UUID studentId) {
        List<Grade> grades = new ArrayList<>();
        try (StudentGradesCursorWrapper cursor = queryGrades(
                AppDbSchema.GradeTable.Cols.STUDENT_ID + " = ?",
                new String[]{studentId.toString()}
        )) {
            while (cursor.moveToNext()) {
                grades.add(cursor.getGrade());
            }
        }
        return grades;
    }

    public List<Grade> getGradesForEvaluation(UUID evaluationId) {
        List<Grade> grades = new ArrayList<>();
        try (StudentGradesCursorWrapper cursor = queryGrades(
                AppDbSchema.GradeTable.Cols.EVALUATION_ID + " = ?",
                new String[]{evaluationId.toString()}
        )) {
            while (cursor.moveToNext()) {
                grades.add(cursor.getGrade());
            }
        }
        return grades;
    }

    public void updateGrade(Grade grade) {
        String uuidString = grade.getId().toString();
        ContentValues values = getContentValues(grade);

        mDatabase.update(AppDbSchema.GradeTable.NAME, values,
                AppDbSchema.GradeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private StudentGradesCursorWrapper queryGrades(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                AppDbSchema.GradeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new StudentGradesCursorWrapper(cursor);
    }

    private ContentValues getContentValues(Grade grade) {
        ContentValues values = new ContentValues();
        values.put(AppDbSchema.GradeTable.Cols.UUID, grade.getId().toString());
        values.put(AppDbSchema.GradeTable.Cols.STUDENT_ID, grade.getStudentId().toString());
        values.put(AppDbSchema.GradeTable.Cols.EVALUATION_ID, grade.getEvaluationId().toString());
        values.put(AppDbSchema.GradeTable.Cols.SCORE, grade.getScore());
        return values;
    }
}
