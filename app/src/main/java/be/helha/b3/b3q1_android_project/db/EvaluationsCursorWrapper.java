package be.helha.b3.b3q1_android_project.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import be.helha.b3.b3q1_android_project.models.Evaluation;

public class EvaluationsCursorWrapper extends CursorWrapper {
    public EvaluationsCursorWrapper(Cursor cursor) {super(cursor);}

    public Evaluation getEvaluation() {
        String uuidString= getString(getColumnIndex(AppDbSchema.EvaluationTable.Cols.UUID));
        String name= getString(getColumnIndex(AppDbSchema.EvaluationTable.Cols.NAME));
        String courseId = getString(getColumnIndex(AppDbSchema.EvaluationTable.Cols.COURSE_ID));
        int maxPoint = getInt(getColumnIndex(AppDbSchema.EvaluationTable.Cols.MAX_POINT));
        int score = getInt(getColumnIndex(AppDbSchema.EvaluationTable.Cols.SCORE));
        boolean isSubEvaluation = getInt(getColumnIndex(AppDbSchema.EvaluationTable.Cols.IS_SUB_EVALUATION)) == 1;
        String parentEvaluationId = getString(getColumnIndex(AppDbSchema.EvaluationTable.Cols.PARENT_EVALUATION_ID));

        Evaluation evaluation=new Evaluation(UUID.fromString(uuidString));
        evaluation.setName(name);
        evaluation.setMaxPoint(maxPoint);
        evaluation.setScore(score);
        evaluation.setCourseId(courseId);
        evaluation.setSubEvaluation(isSubEvaluation);
        evaluation.setParentEvaluationId(parentEvaluationId);

        return evaluation;
    }
}