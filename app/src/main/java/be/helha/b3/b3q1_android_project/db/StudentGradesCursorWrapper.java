package be.helha.b3.b3q1_android_project.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import be.helha.b3.b3q1_android_project.models.Grade;

/**
 * A wrapper class for a Cursor that provides convenience methods for retrieving Grade objects.
 */
public class StudentGradesCursorWrapper extends CursorWrapper {

    /**
     * Constructor for StudentGradesCursorWrapper.
     * @param cursor The cursor to wrap.
     */
    public StudentGradesCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * Retrieves a Grade object from the current position of the cursor.
     * @return A Grade object.
     */
    public Grade getGrade() {
        String uuidString = getString(getColumnIndex(AppDbSchema.GradeTable.Cols.UUID));
        String studentIdString = getString(getColumnIndex(AppDbSchema.GradeTable.Cols.STUDENT_ID));
        String evaluationIdString = getString(getColumnIndex(AppDbSchema.GradeTable.Cols.EVALUATION_ID));
        int score = getInt(getColumnIndex(AppDbSchema.GradeTable.Cols.SCORE));

        Grade grade = new Grade(UUID.fromString(uuidString));
        grade.setStudentId(UUID.fromString(studentIdString));
        grade.setEvaluationId(UUID.fromString(evaluationIdString));
        grade.setScore(score);

        return grade;
    }
}