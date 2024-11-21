package be.helha.b3.b3q1_android_project.models;

import java.util.UUID;

public class Evaluation {
    private String mName;
    private UUID mId;
    private Integer mMaxPoint;
    private Integer mScore;
    private String mCourseId;
    private boolean mSubEvaluation;

    public Evaluation(UUID id) {
        this.mId = id;
    }

    public UUID getId() {return mId;}

    public String getName() {return mName;}


    public Integer getMaxPoint() {return mMaxPoint;}

    public Integer getScore() {return mScore;}

    public String getCourseId() {return mCourseId;}

    public boolean isSubEvaluation() {return mSubEvaluation;}

    public void setName(String name) {mName = name;}

    public void setMaxPoint(Integer maxPoint) {mMaxPoint = maxPoint;}

    public void setScore(Integer score) {mScore = score;}

    public void setCourseId(String courseId) {mCourseId = courseId;}

    public void setSubEvaluation(boolean subEvaluation) {mSubEvaluation = subEvaluation;}
}