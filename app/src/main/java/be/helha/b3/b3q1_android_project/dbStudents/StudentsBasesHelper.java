package be.helha.b3.b3q1_android_project.dbStudents;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentsBasesHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "students.db";

    public StudentsBasesHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + StudentsDbSchema.StudentsTable.NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                StudentsDbSchema.StudentsTable.cols.UUID + " TEXT, " +
                StudentsDbSchema.StudentsTable.cols.FIRSTNAME + " TEXT, " +
                StudentsDbSchema.StudentsTable.cols.CLASSE + " TEXT" +
                ")");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
