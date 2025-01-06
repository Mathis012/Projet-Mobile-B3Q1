package be.helha.b3.b3q1_android_project.db;

/**
 * Defines the schema for the application's database.
 */
public final class AppDbSchema {
    private AppDbSchema() {}

    /**
     * Schema for the Class table.
     */
    public static final class ClassTable {
        public static final String NAME = "classes";

        /**
         * Columns for the Class table.
         */
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
        }
    }

    /**
     * Schema for the Course table.
     */
    public static final class CourseTable {
        public static final String NAME = "courses";

        /**
         * Columns for the Course table.
         */
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String CLASS_ID = "class_id";
        }
    }

    /**
     * Schema for the Evaluation table.
     */
    public static final class EvaluationTable {
        public static final String NAME = "evaluations";

        /**
         * Columns for the Evaluation table.
         */
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String SCORE = "score";
            public static final String MAX_POINT = "max_point";
            public static final String IS_SUB_EVALUATION = "is_sub_evaluation";
            public static final String COURSE_ID = "course_id"; // Foreign key to the Course table
            public static final String PARENT_EVALUATION_ID = "parent_evaluation_id";
        }
    }

    /**
     * Schema for the Student table.
     */
    public static final class StudentTable {
        public static final String NAME = "students";

        /**
         * Columns for the Student table.
         */
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String CLASS_ID = "class_id"; // Foreign key to the Class table
        }
    }

    /**
     * Schema for the Grade table.
     */
    public static final class GradeTable {
        public static final String NAME = "grades";

        /**
         * Columns for the Grade table.
         */
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String STUDENT_ID = "student_id"; // Foreign key to the Student table
            public static final String EVALUATION_ID = "evaluation_id"; // Foreign key to the Evaluation table
            public static final String SCORE = "score";
        }
    }
}