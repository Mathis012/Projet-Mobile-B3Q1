package be.helha.b3.b3q1_android_project.dbEvaluations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EvaluationsBasesHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DB_NAME = "evaluations.db";
    public EvaluationsBasesHelper(Context context) {super(context, DB_NAME, null, VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + EvaluationsDbSchema.EvaluationsTable.NAME + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EvaluationsDbSchema.EvaluationsTable.cols.UUID + " TEXT, "
                + EvaluationsDbSchema.EvaluationsTable.cols.NAME + " TEXT, "
                + EvaluationsDbSchema.EvaluationsTable.cols.CLASSE + " INTEGER, "
                + EvaluationsDbSchema.EvaluationsTable.cols.MAX_POINT + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
