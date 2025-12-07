package com.jminiapp.examples.studyplanner;

/**
 * model representing a single study task in the planner.
 */
public class StudyTask {

    private int id;
    private String subject;
    private String topic;
    private String deadline;        
    private int estimatedMinutes;   // estimated study time
    private String status;          // in progress

    public StudyTask() {
        // Required for JSON deserialization
    }

    public StudyTask(int id, String subject, String topic,
                     String deadline, int estimatedMinutes) {
        this.id = id;
        this.subject = subject;
        this.topic = topic;
        this.deadline = deadline;
        this.estimatedMinutes = estimatedMinutes;
        this.status = "pending";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public void setEstimatedMinutes(int estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "[" + id + "] "
                + subject + " - " + topic
                + " | deadline: " + deadline
                + " | " + estimatedMinutes + " min"
                + " | status: " + status;
    }
}
