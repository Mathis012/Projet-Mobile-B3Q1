package be.helha.b3.b3q1_android_project.models;

import java.util.UUID;

/**
 * Model class representing a Course entity.
 */
public class Course {
    private UUID mId;
    private String mName;
    private UUID mClassId;

    /**
     * Constructor for Course.
     * @param id The unique identifier for the course.
     * @param name The name of the course.
     * @param classId The unique identifier for the class associated with the course.
     */
    public Course(UUID id, String name, UUID classId) {
        this.mId = id;
        this.mName = name;
        this.mClassId = classId;
    }

    /**
     * Gets the unique identifier of the course.
     * @return The unique identifier of the course.
     */
    public UUID getId() {
        return mId;
    }

    /**
     * Gets the name of the course.
     * @return The name of the course.
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets the name of the course.
     * @param name The new name of the course.
     */
    public void setName(String name) {
        mName = name;
    }

    /**
     * Gets the unique identifier of the class associated with the course.
     * @return The unique identifier of the class.
     */
    public UUID getClassId() {
        return mClassId;
    }
}