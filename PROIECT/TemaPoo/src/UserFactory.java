

public class UserFactory {

    public User getUser(String type, String firstname, String lastname) {
        if (type.equals("Teacher"))
            return new Teacher(firstname, lastname);
        if (type.equals("Student"))
            return new Student(firstname, lastname);
        if (type.equals("Assistant"))
            return new Assistant(firstname, lastname);
        if (type.equals("Parent"))
            return new Parent(firstname, lastname);
        return null;
    }
}