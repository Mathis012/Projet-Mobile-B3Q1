package be.helha.b3.b3q1_android_project.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Model class representing an Evaluation entity.
 */
public class Evaluation {
    private String mName;
    private UUID mId;
    private Integer mMaxPoint;
    private Integer mScore;
    private String mCourseId;
    private boolean mSubEvaluation;
    private String parentEvaluationId;
    private List<Evaluation> subEvaluations;

    /**
     * Constructor for Evaluation.
     * @param id The unique identifier for the evaluation.
     */
    public Evaluation(UUID id) {
        this.mId = id;
        this.subEvaluations = new ArrayList<>();
    }

    /**
     * Gets the unique identifier of the evaluation.
     * @return The unique identifier of the evaluation.
     */
    public UUID getId() {
        return mId;
    }

    /**
     * Gets the name of the evaluation.
     * @return The name of the evaluation.
     */
    public String getName() {
        return mName;
    }

    /**
     * Gets the maximum points for the evaluation.
     * @return The maximum points for the evaluation.
     */
    public Integer getMaxPoint() {
        return mMaxPoint;
    }

    /**
     * Gets the score of the evaluation.
     * @return The score of the evaluation.
     */
    public Integer getScore() {
        return mScore;
    }

    /**
     * Gets the course ID associated with the evaluation.
     * @return The course ID associated with the evaluation.
     */
    public String getCourseId() {
        return mCourseId;
    }

    /**
     * Checks if the evaluation is a sub-evaluation.
     * @return True if the evaluation is a sub-evaluation, false otherwise.
     */
    public boolean isSubEvaluation() {
        return mSubEvaluation;
    }

    /**
     * Sets the name of the evaluation.
     * @param name The new name of the evaluation.
     */
    public void setName(String name) {
        mName = name;
    }

    /**
     * Sets the maximum points for the evaluation.
     * @param maxPoint The new maximum points for the evaluation.
     */
    public void setMaxPoint(Integer maxPoint) {
        mMaxPoint = maxPoint;
    }

    /**
     * Sets the score of the evaluation.
     * @param score The new score of the evaluation.
     */
    public void setScore(Integer score) {
        mScore = score;
    }

    /**
     * Sets the course ID associated with the evaluation.
     * @param courseId The new course ID associated with the evaluation.
     */
    public void setCourseId(String courseId) {
        mCourseId = courseId;
    }

    /**
     * Sets whether the evaluation is a sub-evaluation.
     * @param subEvaluation True if the evaluation is a sub-evaluation, false otherwise.
     */
    public void setSubEvaluation(boolean subEvaluation) {
        mSubEvaluation = subEvaluation;
    }

    /**
     * Gets the parent evaluation ID.
     * @return The parent evaluation ID.
     */
    public String getParentEvaluationId() {
        return parentEvaluationId;
    }

    /**
     * Sets the parent evaluation ID.
     * @param parentEvaluationId The new parent evaluation ID.
     */
    public void setParentEvaluationId(String parentEvaluationId) {
        this.parentEvaluationId = parentEvaluationId;
    }

    /**
     * Gets the list of sub-evaluations.
     * @return The list of sub-evaluations.
     */
    public List<Evaluation> getSubEvaluations() {
        return subEvaluations;
    }

    /**
     * Adds a sub-evaluation to the list of sub-evaluations.
     * @param subEvaluation The sub-evaluation to add.
     */
    public void addSubEvaluation(Evaluation subEvaluation) {
        subEvaluations.add(subEvaluation);
    }
}