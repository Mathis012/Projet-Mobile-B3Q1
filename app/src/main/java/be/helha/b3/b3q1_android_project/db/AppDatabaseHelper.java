package be.helha.b3.b3q1_android_project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 3;
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
                AppDbSchema.EvaluationTable.Cols.SCORE + " INTEGER, " +
                AppDbSchema.EvaluationTable.Cols.COURSE_ID + " TEXT" +
                ")");

        db.execSQL("CREATE TABLE " + AppDbSchema.StudentTable.NAME + "(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AppDbSchema.StudentTable.Cols.UUID + " TEXT, " +
                AppDbSchema.StudentTable.Cols.NAME + " TEXT, " +
                AppDbSchema.StudentTable.Cols.CLASS_ID + " TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL("DROP TABLE IF EXISTS " + AppDbSchema.CourseTable.NAME);
            db.execSQL("CREATE TABLE " + AppDbSchema.CourseTable.NAME + "(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AppDbSchema.CourseTable.Cols.UUID + " TEXT, " +
                    AppDbSchema.CourseTable.Cols.NAME + " TEXT, " +
                    AppDbSchema.CourseTable.Cols.CLASS_ID + " TEXT" +
                    ")");
        }
    }
}
