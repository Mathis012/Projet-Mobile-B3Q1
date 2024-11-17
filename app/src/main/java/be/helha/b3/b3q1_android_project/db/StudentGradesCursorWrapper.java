package be.helha.b3.b3q1_android_project.db;

import static android.provider.Settings.System.getString;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import be.helha.b3.b3q1_android_project.models.Grade;

public class StudentGradesCursorWrapper extends CursorWrapper {
    public StudentGradesCursorWrapper(Cursor cursor) {
        super(cursor);
    }

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
