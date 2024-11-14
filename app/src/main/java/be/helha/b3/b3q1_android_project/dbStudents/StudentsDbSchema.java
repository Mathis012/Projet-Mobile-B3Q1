package be.helha.b3.b3q1_android_project.dbStudents;

public abstract class StudentsDbSchema {
    public static final class StudentsTable {
        public static final String NAME = "students";
        public static final class cols {
            public static final String UUID = "uuid";
            public static final String FIRSTNAME = "firstname";
            public static final String CLASSE = "classe";
        }
    }
}
