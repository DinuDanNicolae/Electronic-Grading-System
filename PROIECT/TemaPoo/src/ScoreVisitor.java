
import java.util.*;

public class ScoreVisitor implements Visitor{

    public Map<Teacher, List<Tuple>> examScores = new HashMap<>();
    public Map<Assistant, List<Tuple>> partialScores = new HashMap<>();

    public void addPartialGrade(Assistant assistant, Student student, double partialGrade, String name) {
        List<Tuple> tuple = new ArrayList<>();
        Tuple t = new Tuple(student, name, partialGrade);
        tuple.add(t);
        partialScores.put(assistant,tuple);
    }

    public void addExamGrade(Teacher teacher, Student student, double examGrade, String name) {
        List<Tuple> tuple = new ArrayList<>();
        Tuple t = new Tuple(student,name,examGrade);
        tuple.add(t);
        examScores.put(teacher,tuple);
    }

    private static class Tuple<T1, T2, T3> {
        private T1 student;
        private T2 course;
        private T3 score;

        public Tuple(T1 student, T2 course, T3 score) {
            this.student = student;
            this.course = course;
            this.score = score;
        }

        public T1 getStudent() { return student; }
        public T2 getCourse() { return course; }
        public T3 getScore() { return score; }
        public String toString() {
            return student + " " + score + " " + course;
        }
    }

    @Override
    public void visit(Assistant assistant) {

        List<Tuple> tuple = partialScores.get(assistant);

        if(tuple == null) {
            System.out.println("Empty list");
            return;
        }

        Catalog catalog = Catalog.getInstance();
        List<Course> courses = catalog.getCourses();

        for(Tuple<Student, String, Double> tupleElement : tuple) {
            Student student = tupleElement.getStudent();
            for(Course course: courses) {
                if(course.getName().equals(tupleElement.getCourse())) {
                    Grade grade = course.getGrade(student);
                    if(grade == null) {
                        grade = new Grade(student,tupleElement.getCourse());
                        grade.setPartialScore(tupleElement.getScore());
                        grade.setExamScore(0.0);
//                        System.out.println("Studentul: " + student + " a primit nota: " + grade.getPartialScore());
                        course.addGrade(grade);
                      catalog.notifyObservers(grade);
                    }
                    else {
                        grade.setPartialScore(tupleElement.getScore());
//                      course.addGrade(grade);
                      catalog.notifyObservers(course.getGrade(student));
                    }
                }
            }
        }
    }

    @Override
    public void visit(Teacher teacher) {
        List<Tuple> tuple = examScores.get(teacher);

        if(tuple == null) {
            System.out.println("Empty list");
            return;
        }

        Catalog catalog = Catalog.getInstance();
        List<Course> courses = catalog.getCourses();

        for(Tuple<Student, String, Double> tupleElement : tuple) {
            Student student = tupleElement.getStudent();
            for(Course course: courses) {
                if(course.getName().equals(tupleElement.getCourse())) {
                    Grade grade = course.getGrade(student);
                    if(grade == null) {
                        grade = new Grade(student,tupleElement.getCourse());
                        grade.setStudent(student);
                        grade.setCourse(course.getName());
                        grade.setExamScore(tupleElement.getScore());
                        grade.setPartialScore(0.0);
                        course.addGrade(grade);
                       catalog.notifyObservers(grade);
                    }
                    else {
                        grade.setExamScore(tupleElement.getScore());
//                       course.addGrade(grade);
                        catalog.notifyObservers(course.getGrade(student));
                    }
                }
            }
        }
    }
}
