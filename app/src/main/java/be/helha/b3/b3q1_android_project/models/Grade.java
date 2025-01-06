package be.helha.b3.b3q1_android_project.models;

import java.util.UUID;

/**
 * Model class representing a Grade entity.
 */
public class Grade {
    private UUID mId;
    private UUID mStudentId;
    private UUID mEvaluationId;
    private int mScore;

    /**
     * Constructor for Grade.
     * @param id The unique identifier for the grade.
     */
    public Grade(UUID id) {
        mId = id;
    }

    /**
     * Gets the unique identifier of the grade.
     * @return The unique identifier of the grade.
     */
    public UUID getId() {
        return mId;
    }

    /**
     * Gets the unique identifier of the student associated with the grade.
     * @return The unique identifier of the student.
     */
    public UUID getStudentId() {
        return mStudentId;
    }

    /**
     * Gets the unique identifier of the evaluation associated with the grade.
     * @return The unique identifier of the evaluation.
     */
    public UUID getEvaluationId() {
        return mEvaluationId;
    }

    /**
     * Gets the score of the grade.
     * @return The score of the grade.
     */
    public int getScore() {
        return mScore;
    }

    /**
     * Sets the unique identifier of the student associated with the grade.
     * @param studentId The new student ID.
     */
    public void setStudentId(UUID studentId) {
        mStudentId = studentId;
    }

    /**
     * Sets the unique identifier of the evaluation associated with the grade.
     * @param evaluationId The new evaluation ID.
     */
    public void setEvaluationId(UUID evaluationId) {
        mEvaluationId = evaluationId;
    }

    /**
     * Sets the score of the grade.
     * @param score The new score.
     */
    public void setScore(int score) {
        mScore = score;
    }
}