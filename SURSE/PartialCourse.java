
import java.util.ArrayList;

public class PartialCourse extends Course{
    public PartialCourse(PartialCourseBuilder builder) {
        super(builder);
    }

        public static class PartialCourseBuilder extends CourseBuilder {
            public PartialCourseBuilder(String name, Teacher professor, int creditPoints) {
                super(name,professor,creditPoints);
            }
            public Course build() {
                return new PartialCourse(this);
            }
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduatedStudents = new ArrayList<>();
        for(Student student : getAllStudents()) {
            Grade grade;
            grade = this.getGrade(student);
            if(grade.getTotal() >= 5) {
                graduatedStudents.add(student);
            }
        }
        return graduatedStudents;
    }
}
