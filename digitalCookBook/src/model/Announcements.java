package model;

import java.sql.Timestamp;

public class Announcements {
    private int id;
    private String title;
    private String message;
    private String targetAudience; // CHEF, VIEWER, BOTH
//    private int createdBy;
    private Timestamp createdAt;

    // Constructor
    public Announcements(int id, String title, String message, String targetAudience, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.targetAudience = targetAudience;
//        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

//    public int getCreatedBy() {
//        return createdBy;
//    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

//    public void setCreatedBy(int createdBy) {
//        this.createdBy = createdBy;
//    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
