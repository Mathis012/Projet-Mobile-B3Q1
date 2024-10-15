package be.helha.b3.b3q1_android_project.models;

import java.util.UUID;

public class Classe {
    private String mName;
    private UUID mId;

    public Classe(UUID id) {
        mId = id;
        mName = "";
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
