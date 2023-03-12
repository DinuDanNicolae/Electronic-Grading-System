
import java.util.ArrayList;

public class FullCourse extends Course {

    public FullCourse(FullCourseBuilder builder) {
        super(builder);
    }

    public static class FullCourseBuilder extends CourseBuilder {
        public FullCourseBuilder(String name, Teacher professor, int creditPoints) {
            super(name,professor,creditPoints);
        }
        public Course build() {
            return new FullCourse(this);
        }
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduatedStudents = new ArrayList<>();
        for (Student student : getAllStudents()) {
            Grade grade;
            grade = this.getGrade(student);
            if (grade.getPartialScore() >= 3 && grade.getExamScore() >= 2) {
                graduatedStudents.add(student);
            }
        }
        return graduatedStudents;
    }
}
