package be.helha.b3.b3q1_android_project.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.db.EvaluationsCursorWrapper;

public class EvaluationLab {
    private static EvaluationLab sEvaluationLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static EvaluationLab get(Context context) {
        if (sEvaluationLab == null) {
            sEvaluationLab = new EvaluationLab(context);
        }
        return sEvaluationLab;
    }

    private EvaluationLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new AppDatabaseHelper(mContext).getWritableDatabase();
    }

    public void addEvaluation(Evaluation evaluation) {
        mDatabase.insert(AppDbSchema.EvaluationTable.NAME, null, getContentValues(evaluation));
    }

    private ContentValues getContentValues(Evaluation evaluation) {
        ContentValues values = new ContentValues();
        values.put(AppDbSchema.EvaluationTable.Cols.UUID, evaluation.getId().toString());
        values.put(AppDbSchema.EvaluationTable.Cols.NAME, evaluation.getName());
        values.put(AppDbSchema.EvaluationTable.Cols.SCORE, evaluation.getScore());
        values.put(AppDbSchema.EvaluationTable.Cols.COURSE_ID, evaluation.getCourseId());
        values.put(AppDbSchema.EvaluationTable.Cols.MAX_POINT, evaluation.getMaxPoint());
        values.put(AppDbSchema.EvaluationTable.Cols.IS_SUB_EVALUATION, evaluation.isSubEvaluation() ? 1 : 0);
        values.put(AppDbSchema.EvaluationTable.Cols.PARENT_EVALUATION_ID, evaluation.getParentEvaluationId());
        return values;
    }

    private EvaluationsCursorWrapper queryEvaluations(String whereClause, String[] whereArgs) {
        return new EvaluationsCursorWrapper(mDatabase.query(
                AppDbSchema.EvaluationTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, null, null
        ));
    }

    public List<Evaluation> getEvaluationsForCourse(String courseId) {
        List<Evaluation> evaluations = new ArrayList<>();
        EvaluationsCursorWrapper cursor = queryEvaluations(
                AppDbSchema.EvaluationTable.Cols.COURSE_ID + " = ?",
                new String[]{courseId}
        );

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                evaluations.add(cursor.getEvaluation());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        Map<String, Evaluation> evaluationMap = new HashMap<>();
        for (Evaluation eval : evaluations) {
            evaluationMap.put(eval.getId().toString(), eval);
        }

        List<Evaluation> topLevelEvaluations = new ArrayList<>();
        for (Evaluation eval : evaluations) {
            String parentId = eval.getParentEvaluationId();
            if (parentId == null) {
                topLevelEvaluations.add(eval);
            } else {
                Evaluation parent = evaluationMap.get(parentId);
                if (parent != null) {
                    parent.addSubEvaluation(eval);
                }
            }
        }
        List<Evaluation> flattenedEvaluations = new ArrayList<>();
        for (Evaluation eval : topLevelEvaluations) {
            addEvaluationAndSubEvaluations(flattenedEvaluations, eval);
        }
        return flattenedEvaluations;
    }

    private void addEvaluationAndSubEvaluations(List<Evaluation> list, Evaluation evaluation) {
        list.add(evaluation);
        for (Evaluation subEval : evaluation.getSubEvaluations()) {
            addEvaluationAndSubEvaluations(list, subEval);
        }
    }
}