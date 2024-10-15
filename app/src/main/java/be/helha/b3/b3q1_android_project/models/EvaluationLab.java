package be.helha.b3.b3q1_android_project.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import be.helha.b3.b3q1_android_project.dbEvaluation.EvaluationsBasesHelper;
import be.helha.b3.b3q1_android_project.dbEvaluation.EvaluationsCursorWrapper;
import be.helha.b3.b3q1_android_project.dbEvaluation.EvaluationsDbSchema;

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
        mDatabase = new EvaluationsBasesHelper(mContext).getWritableDatabase();
    }

    public void addEvaluation(Evaluation evaluation) {
        mDatabase.insert(EvaluationsDbSchema.EvaluationsTable.NAME, null, getContentValues(evaluation));
    }

    public Evaluation getEvaluation(String uuid) {
        EvaluationsCursorWrapper cursor = queryEvaluations(EvaluationsDbSchema.EvaluationsTable.cols.UUID + "=?", new String[]{uuid});
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
        values.put(EvaluationsDbSchema.EvaluationsTable.cols.UUID, evaluation.getId().toString());
        values.put(EvaluationsDbSchema.EvaluationsTable.cols.NAME, evaluation.getName());
        values.put(String.valueOf(EvaluationsDbSchema.EvaluationsTable.cols.CLASSE), evaluation.getClasse());
        values.put(String.valueOf(EvaluationsDbSchema.EvaluationsTable.cols.MAX_POINT), evaluation.getMaxPoint());
        return values;
    }

    private EvaluationsCursorWrapper queryEvaluations(String whereClause, String[] whereArgs) {
        return new EvaluationsCursorWrapper(mDatabase.query(
                EvaluationsDbSchema.EvaluationsTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, null, null
        ));
    }
}
