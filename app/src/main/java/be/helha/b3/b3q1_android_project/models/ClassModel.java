package be.helha.b3.b3q1_android_project.models;

import java.util.UUID;

/**
 * Model class representing a Class entity.
 */
public class ClassModel {
    private String mName;
    private UUID mId;

    /**
     * Constructor for ClassModel.
     * @param id The unique identifier for the class.
     * @param name The name of the class.
     */
    public ClassModel(UUID id, String name) {
        this.mId = id;
        this.mName = name;
    }

    /**
     * Gets the unique identifier of the class.
     * @return The unique identifier of the class.
     */
    public UUID getId() {
        return mId;
    }

    /**
     * Gets the name of the class.
     * @return The name of the class.
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets the name of the class.
     * @param name The new name of the class.
     */
    public void setName(String name) {
        mName = name;
    }
}