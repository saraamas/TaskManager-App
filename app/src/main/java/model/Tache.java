package model;

public class Tache {
    private String title;
    private String description;
    private String deadline;
    private String img;
    private String doc_uri;

    private String userId;
    private boolean isDone; // New field to represent the status of the task



    public Tache(String title, String description, String deadline, String img, String userId){
        this.title=title;
        this.description=description;
        this.deadline=deadline;
        this.userId = userId;
        this.img=img;
        this.isDone = false; // By default, task is not done when created

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDoc_uri() {
        return doc_uri;
    }

    public void setDoc_uri(String doc_uri) {
        this.doc_uri = doc_uri;
    }
    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    // Method to get the icon representing the task status
    public String getStatusIcon() {
        return isDone ? "done_icon.png" : "undone_icon.png";
        // Change "done_icon.png" and "undone_icon.png" to the actual file names or paths of your icon images
    }
}
