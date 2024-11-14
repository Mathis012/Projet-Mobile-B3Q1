package be.helha.b3.b3q1_android_project.models;

import java.util.UUID;

public class Student {
    private String mFirstName;
    private UUID mId;
    private String mClasse;

    public Student(UUID id) {
        mId = id;
        mFirstName = "";
        mClasse = "";
    }

    public UUID getId() {return mId;}

    public String getFirstName() {return mFirstName;}

    public String getClasse() {return mClasse;}

    public void setFirstName(String firstName) {mFirstName = firstName;}

    public void setClasse(String classe) {mClasse = classe;}
}
