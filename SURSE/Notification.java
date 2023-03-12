

public class Notification {

    private String message;
    private Grade grade;

    public Notification(String message, Grade grade) {
        this.message = message;
        this.grade = grade;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "regarding student for: "+ grade.getCourse() + " Name " + grade.getStudent().getName() + "{" + "message='" + message + '\'' + ", grade=" + grade + '}';
    }
}
