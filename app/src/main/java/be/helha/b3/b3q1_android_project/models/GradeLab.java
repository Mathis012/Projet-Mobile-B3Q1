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

/**
 * Singleton class that manages the database operations for Grade objects.
 */
public class GradeLab {
    private static GradeLab sGradeLab;
    private final Context mContext;
    private final SQLiteDatabase mDatabase;

    /**
     * Private constructor to prevent direct instantiation.
     * @param context The application context.
     */
    private GradeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new AppDatabaseHelper(mContext).getWritableDatabase();
    }

    /**
     * Returns the singleton instance of GradeLab.
     * @param context The application context.
     * @return The singleton instance of GradeLab.
     */
    public static GradeLab get(Context context) {
        if (sGradeLab == null) {
            sGradeLab = new GradeLab(context);
        }
        return sGradeLab;
    }

    /**
     * Adds a Grade to the database.
     * @param grade The Grade to add.
     */
    public void addGrade(Grade grade) {
        ContentValues values = getContentValues(grade);
        mDatabase.insert(AppDbSchema.GradeTable.NAME, null, values);
    }

    /**
     * Retrieves a list of Grades for a given student.
     * @param studentId The ID of the student.
     * @return A list of Grades for the student.
     */
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

    /**
     * Retrieves a list of Grades for a given evaluation.
     * @param evaluationId The ID of the evaluation.
     * @return A list of Grades for the evaluation.
     */
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

    /**
     * Updates a Grade in the database.
     * @param grade The Grade to update.
     */
    public void updateGrade(Grade grade) {
        String uuidString = grade.getId().toString();
        ContentValues values = getContentValues(grade);

        mDatabase.update(AppDbSchema.GradeTable.NAME, values,
                AppDbSchema.GradeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    /**
     * Queries the database for Grades matching the given criteria.
     * @param whereClause The SQL WHERE clause.
     * @param whereArgs The arguments for the WHERE clause.
     * @return A wrapper for the resulting cursor.
     */
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

    /**
     * Converts a Grade object to ContentValues.
     * @param grade The Grade to convert.
     * @return The ContentValues representing the Grade.
     */
    private ContentValues getContentValues(Grade grade) {
        ContentValues values = new ContentValues();
        values.put(AppDbSchema.GradeTable.Cols.UUID, grade.getId().toString());
        values.put(AppDbSchema.GradeTable.Cols.STUDENT_ID, grade.getStudentId().toString());
        values.put(AppDbSchema.GradeTable.Cols.EVALUATION_ID, grade.getEvaluationId().toString());
        values.put(AppDbSchema.GradeTable.Cols.SCORE, grade.getScore());
        return values;
    }
}