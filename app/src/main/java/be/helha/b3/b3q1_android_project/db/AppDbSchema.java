package be.helha.b3.b3q1_android_project.db;

public final class AppDbSchema {
    private AppDbSchema() {}

    public static final class ClassTable {
        public static final String NAME = "classes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
        }
    }

    public static final class CourseTable {
        public static final String NAME = "courses";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String CLASS_ID = "class_id";
        }
    }

    public static final class EvaluationTable {
        public static final String NAME = "evaluations";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String SCORE = "score";
            public static final String CLASSE = "classe";
            public static final String MAX_POINT = "max_point";
            public static final String COURSE_ID = "course_id"; // Clé étrangère vers la table Course
        }
    }

    public static final class StudentTable {
        public static final String NAME = "students";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String CLASS_ID = "class_id"; // Clé étrangère vers la table Class
        }
    }
}
