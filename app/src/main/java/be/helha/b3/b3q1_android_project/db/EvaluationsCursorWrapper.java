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
        String classe= getString(getColumnIndex(AppDbSchema.EvaluationTable.Cols.CLASSE));
        int maxPoint= getInt(getColumnIndex(AppDbSchema.EvaluationTable.Cols.MAX_POINT));

        Evaluation evaluation=new Evaluation(UUID.fromString(uuidString));
        evaluation.setName(name);
        evaluation.setClasse(Integer.valueOf(classe));
        evaluation.setMaxPoint(maxPoint);

        return evaluation;
    }
}
