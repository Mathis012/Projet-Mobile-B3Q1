package be.helha.b3.b3q1_android_project.models;

import java.util.UUID;

public class Student {
    private String mFirstName;
    private UUID mId;
    private Integer mClasse;

    public Student(UUID id) {
        mId = id;
        mFirstName = "";
        mClasse = null;
    }

    public UUID getId() {return mId;}

    public String getFirstName() {return mFirstName;}

    public Integer getClasse() {return mClasse;}

    public void setFirstName(String firstName) {mFirstName = firstName;}

    public void setClasse(Integer classe) {mClasse = classe;}
}
