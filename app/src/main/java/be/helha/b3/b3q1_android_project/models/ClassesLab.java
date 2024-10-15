package be.helha.b3.b3q1_android_project.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import be.helha.b3.b3q1_android_project.dbClasses.ClassesBasesHelper;
import be.helha.b3.b3q1_android_project.dbClasses.ClassesCursorWrapper;
import be.helha.b3.b3q1_android_project.dbClasses.ClassesDbSchema;

public class ClassesLab {
    private static ClassesLab sClassesLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ClassesLab get(Context context)
    {
        if(sClassesLab==null)
        {
            sClassesLab=new ClassesLab(context);
        }
        return sClassesLab;
    }

    private ClassesLab(Context context)
    {
        mContext=context.getApplicationContext();
        mDatabase=new ClassesBasesHelper(mContext).getWritableDatabase();
    }

    public void addClasse(Classe classe)
    {
        mDatabase.insert(ClassesDbSchema.ClassesTable.NAME,null,getContentValues(classe));
    }

    public Classe getClasse(String uuid)
    {
        ClassesCursorWrapper cursor= queryClasses(ClassesDbSchema.ClassesTable.cols.UUID+"=?", new String[]{uuid});
        try
        {
            if(cursor.getCount()==0)
                return null;
            cursor.moveToFirst();
            return cursor.getClasse();
        }
        finally
        {
            cursor.close();
        }
    }

    public List<Classe> getClasses()
    {
        ArrayList<Classe> classes=new ArrayList<>();
        ClassesCursorWrapper cursor=queryClasses(null,null);
        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                classes.add(cursor.getClasse());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }
        return classes;
    }

    private ContentValues getContentValues(Classe classe)
    {
        ContentValues values=new ContentValues();
        values.put(ClassesDbSchema.ClassesTable.cols.UUID,classe.getId().toString());
        values.put(ClassesDbSchema.ClassesTable.cols.NAME,classe.getName());
        return values;
    }

    private ClassesCursorWrapper queryClasses(String whereClause, String[] whereArgs)
    {
        return new ClassesCursorWrapper(mDatabase.query(
                ClassesDbSchema.ClassesTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, null, null));
    }
}
