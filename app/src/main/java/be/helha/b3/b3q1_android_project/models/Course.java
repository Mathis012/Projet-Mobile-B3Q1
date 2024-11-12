package be.helha.b3.b3q1_android_project.models;

import java.util.UUID;

public class Course {
    private UUID mId;
    private String mName;
    private UUID mClassId;

    public Course(UUID id, String name, UUID classeId) {
        this.mId = id;
        this.mName = name;
        this.mClassId = classeId;
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

    public UUID getClasseId() {
        return mClassId;
    }
}