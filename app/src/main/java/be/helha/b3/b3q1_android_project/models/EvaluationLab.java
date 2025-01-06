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

/**
 * Singleton class that manages the database operations for Evaluation objects.
 */
public class EvaluationLab {
    private static EvaluationLab sEvaluationLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    /**
     * Returns the singleton instance of EvaluationLab.
     * @param context The application context.
     * @return The singleton instance of EvaluationLab.
     */
    public static EvaluationLab get(Context context) {
        if (sEvaluationLab == null) {
            sEvaluationLab = new EvaluationLab(context);
        }
        return sEvaluationLab;
    }

    /**
     * Private constructor to prevent direct instantiation.
     * @param context The application context.
     */
    private EvaluationLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new AppDatabaseHelper(mContext).getWritableDatabase();
    }

    /**
     * Adds an Evaluation to the database.
     * @param evaluation The Evaluation to add.
     */
    public void addEvaluation(Evaluation evaluation) {
        mDatabase.insert(AppDbSchema.EvaluationTable.NAME, null, getContentValues(evaluation));
    }

    /**
     * Converts an Evaluation object to ContentValues.
     * @param evaluation The Evaluation to convert.
     * @return The ContentValues representing the Evaluation.
     */
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

    /**
     * Queries the database for Evaluations matching the given criteria.
     * @param whereClause The SQL WHERE clause.
     * @param whereArgs The arguments for the WHERE clause.
     * @return A wrapper for the resulting cursor.
     */
    private EvaluationsCursorWrapper queryEvaluations(String whereClause, String[] whereArgs) {
        return new EvaluationsCursorWrapper(mDatabase.query(
                AppDbSchema.EvaluationTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, null, null
        ));
    }

    /**
     * Retrieves a list of Evaluations for a given course.
     * @param courseId The ID of the course.
     * @return A list of Evaluations for the course.
     */
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

    /**
     * Recursively adds an Evaluation and its sub-evaluations to a list.
     * @param list The list to add to.
     * @param evaluation The Evaluation to add.
     */
    private void addEvaluationAndSubEvaluations(List<Evaluation> list, Evaluation evaluation) {
        list.add(evaluation);
        for (Evaluation subEval : evaluation.getSubEvaluations()) {
            addEvaluationAndSubEvaluations(list, subEval);
        }
    }
}