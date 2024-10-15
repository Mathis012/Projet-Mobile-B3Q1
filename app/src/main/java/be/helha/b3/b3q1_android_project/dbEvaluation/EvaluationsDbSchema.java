package be.helha.b3.b3q1_android_project.dbEvaluation;

public abstract class EvaluationsDbSchema {
    public static final class EvaluationsTable {
        public static final String NAME = "evaluations";
        public static final class cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final Integer CLASSE = null;
            public static final Integer MAX_POINT = null;
        }
    }
}
