

public class Student extends User implements Comparable<Student> {

    private Parent mother;
    private Parent father;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public void setMother(Parent mother) {
        this.mother = mother;
    }
    public void setFather(Parent father) {
        this.father = father;
    }

    public Parent getMother() {
        return this.mother;
    }

    public Parent getFather() {
        return this.father;
    }

    @Override
    public int compareTo(Student o) {
        return this.getName().compareTo(o.getName());
    }


}
