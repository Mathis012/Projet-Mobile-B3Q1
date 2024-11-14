package be.helha.b3.b3q1_android_project.dbCourses;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CoursesBasesHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "courses.db";
    public CoursesBasesHelper(Context context) { super(context, DATABASE_NAME, null, VERSION); }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CoursesDbSchema.CoursesTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CoursesDbSchema.CoursesTable.cols.UUID + ", " +
                CoursesDbSchema.CoursesTable.cols.NAME + ", " +
                CoursesDbSchema.CoursesTable.cols.CLASS_ID +
                ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            if (!isColumnExists(db, CoursesDbSchema.CoursesTable.NAME, CoursesDbSchema.CoursesTable.cols.CLASS_ID)){
                db.execSQL("ALTER TABLE " + CoursesDbSchema.CoursesTable.NAME + " ADD COLUMN " + CoursesDbSchema.CoursesTable.cols.CLASS_ID + " TEXT;");
            }
        }
    }

    private boolean isColumnExists(SQLiteDatabase db, String tableName, String columnName) {
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
        try {
            int nameIndex = cursor.getColumnIndexOrThrow("name");
            while (cursor.moveToNext()) {
                if (columnName.equals(cursor.getString(nameIndex))) {
                    return true;
                }
            }
        } finally {
            cursor.close();
        }
        return false;
    }

}
