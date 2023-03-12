
import java.util.Comparator;
import java.util.TreeSet;

public class Group extends TreeSet<Student> {
    private Assistant assistant;
    private String ID;

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        super(comp);
        this.ID = ID;
        this.assistant = assistant;
    }

    public Group(String ID, Assistant assistant) {
        this.ID = ID;
        this.assistant = assistant;
    }

    public boolean equal(Group a) {
        if(this.ID == a.ID)
            return true;
        else
            return false;
    }

    public String getID() {
        return this.ID;
    }

    public Assistant getAssistant(){
        return assistant;
    }
    public void setAssistant(Assistant a) {
        this.assistant = a;
    }
    public void setStudent(Student student) {
        this.add(student);
    }

    public TreeSet<Student> getStudents() {
        return this;
    }

}
