package be.helha.b3.b3q1_android_project.models;

import java.util.UUID;

public class Grade {
    private UUID mId;
    private UUID mStudentId;
    private UUID mEvaluationId;
    private int mScore;

    public Grade(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public UUID getStudentId() {
        return mStudentId;
    }

    public UUID getEvaluationId() {
        return mEvaluationId;
    }

    public int getScore() {
        return mScore;
    }

    public void setStudentId(UUID studentId) {
        mStudentId = studentId;
    }

    public void setEvaluationId(UUID evaluationId) {
        mEvaluationId = evaluationId;
    }

    public void setScore(int score) {
        mScore = score;
    }
}
