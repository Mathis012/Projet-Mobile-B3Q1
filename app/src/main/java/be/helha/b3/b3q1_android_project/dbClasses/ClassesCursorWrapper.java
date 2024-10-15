package be.helha.b3.b3q1_android_project.dbClasses;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import be.helha.b3.b3q1_android_project.models.Class;

public class ClassesCursorWrapper extends CursorWrapper {
    public ClassesCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Class getClasse() {
        String uuidString=
                getString(getColumnIndex(ClassesDbSchema.ClassesTable.cols.UUID));
        String name=
                getString(getColumnIndex(ClassesDbSchema.ClassesTable.cols.NAME));
        return new Class (UUID.fromString(uuidString), name);
    }
}
