package be.helha.b3.b3q1_android_project.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public Evaluation getEvaluation(String uuid) {
        EvaluationsCursorWrapper cursor = queryEvaluations(AppDbSchema.EvaluationTable.Cols.UUID + "=?", new String[]{uuid});
        try {
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getEvaluation();
        } finally {
            cursor.close();
        }
    }

    public List<Evaluation> getEvaluations() {
        ArrayList<Evaluation> evaluations = new ArrayList<>();
        EvaluationsCursorWrapper cursor = queryEvaluations(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                evaluations.add(cursor.getEvaluation());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return evaluations;
    }

    private ContentValues getContentValues(Evaluation evaluation) {
        ContentValues values = new ContentValues();
        values.put(AppDbSchema.EvaluationTable.Cols.UUID, evaluation.getId().toString());
        values.put(AppDbSchema.EvaluationTable.Cols.NAME, evaluation.getName());
        values.put(AppDbSchema.EvaluationTable.Cols.SCORE, evaluation.getScore());
        values.put(AppDbSchema.EvaluationTable.Cols.COURSE_ID, evaluation.getCourseId());
        values.put(AppDbSchema.EvaluationTable.Cols.MAX_POINT, evaluation.getMaxPoint());
        values.put(AppDbSchema.EvaluationTable.Cols.IS_SUB_EVALUATION, evaluation.isSubEvaluation() ? 1 : 0);
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
        EvaluationsCursorWrapper cursor = queryEvaluations(AppDbSchema.EvaluationTable.Cols.COURSE_ID + " = ?",
                new String[]{String.valueOf(courseId)});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                evaluations.add(cursor.getEvaluation());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return evaluations;
    }

}
