package be.helha.b3.b3q1_android_project.dbCourses;

public abstract class CoursesDbSchema {
    public static final class CoursesTable {
        public static final String NAME = "courses";
        public static final class cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String CLASS_ID = "class_id";
        }
    }
}
