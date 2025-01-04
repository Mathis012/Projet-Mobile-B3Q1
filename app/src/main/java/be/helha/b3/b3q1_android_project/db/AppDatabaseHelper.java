package be.helha.b3.b3q1_android_project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 8;
    private static final String DATABASE_NAME = "appDatabase.db";

    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + AppDbSchema.ClassTable.NAME + "(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AppDbSchema.ClassTable.Cols.UUID + " TEXT, " +
                AppDbSchema.ClassTable.Cols.NAME + " TEXT" +
                ")");

        db.execSQL("CREATE TABLE " + AppDbSchema.CourseTable.NAME + "(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AppDbSchema.CourseTable.Cols.UUID + " TEXT, " +
                AppDbSchema.CourseTable.Cols.NAME + " TEXT, " +
                AppDbSchema.CourseTable.Cols.CLASS_ID + " TEXT" +
                ")");

        db.execSQL("CREATE TABLE " + AppDbSchema.EvaluationTable.NAME + "(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AppDbSchema.EvaluationTable.Cols.UUID + " TEXT, " +
                AppDbSchema.EvaluationTable.Cols.NAME + " TEXT, " +
                AppDbSchema.EvaluationTable.Cols.SCORE + " INTEGER, " +
                AppDbSchema.EvaluationTable.Cols.MAX_POINT + " INTEGER, " +
                AppDbSchema.EvaluationTable.Cols.COURSE_ID + " INTEGER, " +
                AppDbSchema.EvaluationTable.Cols.IS_SUB_EVALUATION + " INTEGER, " +
                AppDbSchema.EvaluationTable.Cols.PARENT_EVALUATION_ID + " TEXT" +
                ")");

        db.execSQL("CREATE TABLE " + AppDbSchema.StudentTable.NAME + "(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AppDbSchema.StudentTable.Cols.UUID + " TEXT, " +
                AppDbSchema.StudentTable.Cols.NAME + " TEXT, " +
                AppDbSchema.StudentTable.Cols.CLASS_ID + " TEXT" +
                ")");

        db.execSQL("CREATE TABLE " + AppDbSchema.GradeTable.NAME + "(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AppDbSchema.GradeTable.Cols.UUID + " TEXT, " +
                AppDbSchema.GradeTable.Cols.STUDENT_ID + " TEXT, " +
                AppDbSchema.GradeTable.Cols.EVALUATION_ID + " TEXT, " +
                AppDbSchema.GradeTable.Cols.SCORE + " INTEGER" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 8) {
            db.execSQL("ALTER TABLE " + AppDbSchema.EvaluationTable.NAME +
                    " ADD COLUMN " + AppDbSchema.EvaluationTable.Cols.IS_SUB_EVALUATION + " INTEGER");

            db.execSQL("ALTER TABLE " + AppDbSchema.EvaluationTable.NAME +
                    " ADD COLUMN " + AppDbSchema.EvaluationTable.Cols.PARENT_EVALUATION_ID + " TEXT");

            db.execSQL("CREATE TABLE " + AppDbSchema.GradeTable.NAME + "(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AppDbSchema.GradeTable.Cols.UUID + " TEXT, " +
                    AppDbSchema.GradeTable.Cols.STUDENT_ID + " TEXT, " +
                    AppDbSchema.GradeTable.Cols.EVALUATION_ID + " TEXT, " +
                    AppDbSchema.GradeTable.Cols.SCORE + " INTEGER" +
                    ")");
        }
    }
}
