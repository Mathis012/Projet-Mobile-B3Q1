package be.helha.b3.b3q1_android_project.dbStudents;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import java.util.UUID;

import be.helha.b3.b3q1_android_project.models.Student;

public class StudentsCursorWrapper extends CursorWrapper {
    public StudentsCursorWrapper(Cursor cursor) { super (cursor); }

    public Student getStudent() {
        try {
            String uuidString = getString(getColumnIndex(StudentsDbSchema.StudentsTable.cols.UUID));
            String firstname = getString(getColumnIndex(StudentsDbSchema.StudentsTable.cols.FIRSTNAME));
            String classe = getString(getColumnIndex(StudentsDbSchema.StudentsTable.cols.CLASSE));
            Student student = new Student(UUID.fromString(uuidString));
            student.setFirstName(firstname);
            student.setClasse(classe);
            return student;
        } catch (Exception e) {
            Log.e("StudentsCursorWrapper", "Error retrieving student from cursor", e);
            return null;
        }
    }
}
