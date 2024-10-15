package be.helha.b3.b3q1_android_project.models;

import java.util.UUID;

public class Evaluation {
    private String mName;
    private UUID mId;
    private Integer mClasse;
    private Integer mMaxPoint;

    public Evaluation(UUID id) {
        mId = id;
        mName = "";
        mClasse = null;
        mMaxPoint = null;
    }

    public UUID getId() {return mId;}

    public String getName() {return mName;}

    public Integer getClasse() {return mClasse;}

    public Integer getMaxPoint() {return mMaxPoint;}

    public void setName(String name) {mName = name;}

    public void setClasse(Integer classe) {mClasse = classe;}

    public void setMaxPoint(Integer maxPoint) {mMaxPoint = maxPoint;}
}
