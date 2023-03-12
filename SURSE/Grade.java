
public class Grade implements Comparable, Cloneable{

    private Double partialScore;
    private Double examScore;
    private Student student;
    private String course;

    public Grade(Student student, String course) {
        this.student = student;
        this.course = course;
    }

    public Double getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Double getTotal() {
        return partialScore + examScore;
    }

    public int compareTo(Object other) {
        Grade grade;
        grade = (Grade) other;
        return this.getTotal().compareTo(grade.getTotal());
    }

    public String toString() {
        return this.student + " are: nota partial: " + this.getPartialScore() + ", nota examen: " + this.getExamScore() + " si total " + this.getTotal();
    }


    public Object clone() {
        Grade clonedGrade = new Grade(this.student, this.course);
        clonedGrade.partialScore = this.partialScore;
        clonedGrade.examScore = this.examScore;
        return clonedGrade;
    }

}
