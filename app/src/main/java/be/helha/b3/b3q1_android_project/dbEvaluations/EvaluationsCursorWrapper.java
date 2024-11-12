package be.helha.b3.b3q1_android_project.dbEvaluations;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import be.helha.b3.b3q1_android_project.models.Evaluation;

public class EvaluationsCursorWrapper extends CursorWrapper {
    public EvaluationsCursorWrapper(Cursor cursor) {super(cursor);}
    public Evaluation getEvaluation()
    {
        String uuidString= getString(getColumnIndex(EvaluationsDbSchema.EvaluationsTable.cols.UUID));
        String name= getString(getColumnIndex(EvaluationsDbSchema.EvaluationsTable.cols.NAME));
        String classe= getString(getColumnIndex(String.valueOf(EvaluationsDbSchema.EvaluationsTable.cols.CLASSE)));
        int maxPoint= getInt(getColumnIndex(String.valueOf(EvaluationsDbSchema.EvaluationsTable.cols.MAX_POINT)));
        Evaluation evaluation=new Evaluation(UUID.fromString(uuidString));
        evaluation.setName(name);
        evaluation.setClasse(Integer.valueOf(classe));
        evaluation.setMaxPoint(maxPoint);
        return evaluation;
    }
}
