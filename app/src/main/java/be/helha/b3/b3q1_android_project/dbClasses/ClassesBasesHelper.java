package be.helha.b3.b3q1_android_project.dbClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClassesBasesHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "classesBase.db";
    public ClassesBasesHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ ClassesDbSchema.ClassesTable.NAME + "("
                + "_id integer PRIMARY KEY AUTOINCREMENT, "
                + ClassesDbSchema.ClassesTable.cols.UUID + ", " + ClassesDbSchema.ClassesTable.cols.NAME + ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //mise à jour db
    }
}
