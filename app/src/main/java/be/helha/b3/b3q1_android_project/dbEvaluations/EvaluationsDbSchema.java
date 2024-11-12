package be.helha.b3.b3q1_android_project.dbEvaluations;

public abstract class EvaluationsDbSchema {
    public static final class EvaluationsTable {
        public static final String NAME = "evaluations";
        public static final class cols {
            public static final String UUID = "uuid";        // TYPE: TEXT
            public static final String NAME = "name";        // TYPE: TEXT
            public static final String CLASSE = "classe";    // TYPE: INTEGER
            public static final String MAX_POINT = "max_point"; // TYPE: INTEGER
        }
    }
}
