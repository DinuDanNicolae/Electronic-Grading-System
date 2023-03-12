
import java.util.*;

public abstract class Course {

    private String name;
    private Teacher professor;
    private HashSet<Assistant> assistants;
    private ArrayList<Grade> grades;
    private HashMap<String, Group> groups;
    private int creditPoints;

    Course(CourseBuilder builder) {
        this.name = builder.name;
        this.professor = builder.professor;
        this.creditPoints = builder.creditPoints;
        this.assistants = builder.assistants;
        this.grades = builder.grades;
        this.groups = builder.groups;
    }

    public abstract static class CourseBuilder {
        private String name;
        private Teacher professor;
        private HashSet<Assistant> assistants = new HashSet<>();
        private ArrayList<Grade> grades = new ArrayList<>();
        private HashMap<String, Group> groups = new HashMap<>();
        private int creditPoints;

        public CourseBuilder(String name, Teacher professor, int creditPoints) {
            this.name = name;
            this.professor = professor;
            this.creditPoints = creditPoints;
        }

        public CourseBuilder assistants(HashSet<Assistant> assistants) {
            this.assistants = assistants;
            return this;
        }

        public CourseBuilder grades(ArrayList<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public CourseBuilder groups(HashMap<String, Group> groups) {
            this.groups = groups;
            return this;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setProfessor(Teacher professor) {
            this.professor = professor;
        }

        public void setCreditPoints(int creditPoints) {
            this.creditPoints = creditPoints;
        }
    }

    public String getName() {
        return name;
    }

    public Teacher getProfessor() {
        return professor;
    }

    public HashSet<Assistant> getAssistants() {
        return assistants;
    }

    public void setAssistants(HashSet<Assistant> assistants) {
        this.assistants = assistants;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }

    public HashMap<String, Group> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<String, Group> groups) {
        this.groups = groups;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public void addAssistant(String ID, Assistant assistant) {
        Group a = groups.get(ID);
        if(a != null) {
            a.setAssistant(assistant);
            groups.put(ID, a);
            if(!assistants.contains(assistant)) {
                assistants.add(assistant);
            }
        }
    }

    public void addStudent(String ID, Student student) {
        Group a = groups.get(ID);
        a.setStudent(student);
        groups.put(ID,a);
    }

    public void addGroup(Group group) {
        groups.put(group.getID(),group);
    }

    public void addGroup(String ID, Assistant assistant) {
        Group group = new Group(ID, assistant);
        groups.put(ID,group);
    }

    public void addGroup(String ID, Assistant assist, Comparator<Student> comp) {
        Group group = new Group(ID, assist, comp);
        groups.put(ID,group);
    }

    public Grade getGrade(Student student) {
        int i;
        for(i=0;i< grades.size();i++) {
            if(grades.get(i).getStudent().getFirstName().equals(student.getFirstName()) && grades.get(i).getStudent().getLastName().equals(student.getLastName())) {
                return grades.get(i);
            }
        }
        return null;
    }

    public Group getStudentGrup(Student student) {
        for(Group g : groups.values()) {
            if(g.contains(student)) {
                    return g;
                }
        }
        return null;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> allStudents = new ArrayList<>();
        for (Group group : groups.values()) {
            allStudents.addAll(group.getStudents());
        }
        return allStudents;

    }

    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> studentGrades = new HashMap<>();
        int i;
        for(i=0;i< grades.size();i++) {
            Grade grade = grades.get(i);
            studentGrades.put(grade.getStudent(),grade);
        }
        return studentGrades;
    }

    public abstract ArrayList<Student> getGraduatedStudents();

    public BestStudentStrategy strategy;

    public void setBestStudentStrategy(BestStudentStrategy strategy) {
        this.strategy = strategy;
    }

    public Student getBestStudent() {
        return this.strategy.selectBestStudent(this);
    }

    public void setCourseStrategy(Course course,String courseStrategy){
        if(courseStrategy.equals("BestExamScore")) {
            course.setBestStudentStrategy(new BestExamScoreStrategy());
        }
        else if(courseStrategy.equals("BestTotalScore")) {
            course.setBestStudentStrategy(new BestTotalScoreStrategy());
        }
        else if(courseStrategy.equals("BestPartialScore")) {
            course.setBestStudentStrategy(new BestPartialScoreStrategy());
        }
    }

    private class Snapshot {
        private ArrayList<Grade> grds = new ArrayList<Grade>();

        public Snapshot(ArrayList<Grade> grades) {
//            this.grades = new ArrayList<>(grades);
            grades.forEach(grade -> {this.grds.add((Grade) grade.clone());});
        }

        public ArrayList<Grade> getGrds() {
            return this.grds;
        }
    }

    private Snapshot snapshot;

    public void makeBackup() {
        snapshot = new Snapshot(this.grades);
    }

    public void undo() {
        this.grades = snapshot.getGrds();
        snapshot = null;
    }



}