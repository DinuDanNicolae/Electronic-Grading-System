

import java.util.ArrayList;

public class Catalog implements Subject {

    private Grade grade;

    private ArrayList<Observer> observers = new ArrayList<>();

    private boolean notification = false;


    private static Catalog instance = null;
    private ArrayList<Course> courses = new ArrayList<>();

    private Catalog() {
        this.courses = new ArrayList<Course>();
    }

    public static Catalog getInstance() {
        if (instance == null) {
            instance = new Catalog();
        }
        return instance;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public Student getStudent(String FirstName, String LastName) {
        for(Course course: this.courses) {
            for(Student student: course.getAllStudents()) {
                if(student.getFirstName().equals(FirstName) && student.getLastName().equals(LastName)){
                    return student;
                }
            }
        }
        return null;
    }


    @Override
    public void addObserver(Observer observer) {
        if(!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public ArrayList<Observer> getObserver() {
        return observers;
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observers.indexOf(observer));
    }

    @Override
    public void notifyObservers(Grade grade) {
        this.grade = grade;
        Student student = grade.getStudent();
        if(student.getFather() != null) {
            Parent parentFather = student.getFather();
            for(Observer obs : observers) {
                if ((parentFather.getLastName().equals(((Parent) obs).getLastName()))) {
                    Notification notification = new Notification("New grade added to catalog", grade);
                    obs.update(notification);
                }
            }
        }
        if(student.getMother() != null) {
            Parent parentMother = student.getMother();
            for(Observer obs : observers) {
                if ((parentMother.getLastName().equals(((Parent) obs).getLastName()))) {
                    Notification notification = new Notification("New grade added to catalog", grade);
                    obs.update(notification);
                }
            }
        }
    }




}
