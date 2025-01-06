package be.helha.b3.b3q1_android_project.models;

import java.util.UUID;

/**
 * Model class representing a Student entity.
 */
public class Student {
    private String mFirstName;
    private UUID mId;
    private String mClasse;

    /**
     * Constructor for Student.
     * @param id The unique identifier for the student.
     * @param firstName The first name of the student.
     * @param classe The class of the student.
     */
    public Student(UUID id, String firstName, String classe) {
        mId = id;
        mFirstName = firstName;
        mClasse = classe;
    }

    /**
     * Gets the unique identifier of the student.
     * @return The unique identifier of the student.
     */
    public UUID getId() {
        return mId;
    }

    /**
     * Gets the first name of the student.
     * @return The first name of the student.
     */
    public String getFirstName() {
        return mFirstName;
    }

    /**
     * Gets the class of the student.
     * @return The class of the student.
     */
    public String getClasse() {
        return mClasse;
    }

    /**
     * Sets the first name of the student.
     * @param firstName The new first name of the student.
     */
    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    /**
     * Sets the class of the student.
     * @param classe The new class of the student.
     */
    public void setClasse(String classe) {
        mClasse = classe;
    }
}