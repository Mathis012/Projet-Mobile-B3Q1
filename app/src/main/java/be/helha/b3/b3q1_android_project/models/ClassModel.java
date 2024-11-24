package be.helha.b3.b3q1_android_project.models;

import java.util.UUID;

public class ClassModel {
    private String mName;
    private UUID mId;

    public ClassModel(UUID id, String name) {
        this.mId = id;
        this.mName = name;
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
