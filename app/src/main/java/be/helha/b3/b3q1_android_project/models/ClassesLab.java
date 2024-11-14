package be.helha.b3.b3q1_android_project.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.db.ClassesCursorWrapper;

public class ClassesLab {
    private static ClassesLab sClassesLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ClassesLab get(Context context)
    {
        if(sClassesLab==null) {
            sClassesLab=new ClassesLab(context);
        }
        return sClassesLab;
    }

    private ClassesLab(Context context) {
        mContext=context.getApplicationContext();
        mDatabase=new AppDatabaseHelper(mContext).getWritableDatabase();
    }

    public void addClasse(Class aClass) {
        mDatabase.insert(AppDbSchema.ClassTable.NAME,null,getContentValues(aClass));
    }

    public Class getClasse(String uuid) {
        ClassesCursorWrapper cursor= queryClasses(AppDbSchema.ClassTable.Cols.UUID+"=?", new String[]{uuid});
        try {
            if(cursor.getCount()==0)
                return null;
            cursor.moveToFirst();
            return cursor.getClasse();
        }
        finally {
            cursor.close();
        }
    }

    public List<Class> getClasses()
    {
        ArrayList<Class> aClasses =new ArrayList<>();
        ClassesCursorWrapper cursor=queryClasses(null,null);
        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                aClasses.add(cursor.getClasse());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return aClasses;
    }

    private ContentValues getContentValues(Class aClass) {
        ContentValues values=new ContentValues();
        values.put(AppDbSchema.ClassTable.Cols.UUID, aClass.getId().toString());
        values.put(AppDbSchema.ClassTable.Cols.NAME, aClass.getName());
        return values;
    }

    private ClassesCursorWrapper queryClasses(String whereClause, String[] whereArgs) {
        return new ClassesCursorWrapper(mDatabase.query(
                AppDbSchema.ClassTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, null, null));
    }
}
